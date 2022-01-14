package com.suleymankayabasi.fourthhomework.service;

import com.suleymankayabasi.fourthhomework.dto.CollectionDTO;
import com.suleymankayabasi.fourthhomework.dto.LoanDTO;
import com.suleymankayabasi.fourthhomework.exception.CollectionNotFoundException;
import com.suleymankayabasi.fourthhomework.exception.LoanNotFoundException;
import com.suleymankayabasi.fourthhomework.exception.UserNotFoundException;
import com.suleymankayabasi.fourthhomework.mapper.CollectionMapper;
import com.suleymankayabasi.fourthhomework.mapper.LoanMapper;
import com.suleymankayabasi.fourthhomework.model.Collection;
import com.suleymankayabasi.fourthhomework.model.Loan;
import com.suleymankayabasi.fourthhomework.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CollectionService implements ICollectionService{

    private static final String loanTotal = "Total";
    private static final String loanNormal = "Normal";
    private static final String loanLateFee = "Late Fee";

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private LoanService loanService;

    //Bir borç tahsilatı yapıldığında, eğer vade tarihi geçmiş ise, gecikme zammı tutarı kadar bir borç kaydı atılır.
    //Kayıt tarihi tahsilat yapılan tarih olur.
    //Bağlı olduğu borç bilgisi muhakkak tutulmalıdır ki hangi borca istinaden bu gecikme zammı oluşmuş, görünebilsin.
    //Asıl borç ve buna bağlı olan, tahsilat anında oluşturulan gecikme zammı borcunun borç tutarları 0(sıfır) yapılır. Ana borç tutarına dokunulmaz.
    //Parçalı tahsilat yapılamaz. Bir borç tahsil edilmek istenirse borcun tamamı ödenmelidir.
    @Override
    public CollectionDTO collect(Long id,BigDecimal collectionAmount) {

        LoanDTO loanDTO = loanService.findLoanById(id);

        if(loanDTO.equals(null)) throw new LoanNotFoundException("Loan not found.");
        Loan loan = LoanMapper.INSTANCE.convertLoanDTOtoLoan(loanDTO);

        if(loanService.calculateLoan(loan).compareTo(collectionAmount)>0){
            throw new CollectionNotFoundException("Collection amount is not enough!");
        }

        Collection collection = new Collection();
        collection.setPrincipalDebtAmount(loanDTO.getPrincipalDebt());
        collection.setLoanAmount(loanService.calculateLoan(id));
        collection.setRegistrationDate(LocalDate.now());
        collection.setCollectionLoanType(loanTotal);
        collection.setLoanId(id);
        collection.setLoanUserId(loanDTO.getUserId());
        collectionRepository.save(collection);

        Collection collection0 = collectionRepository.findCollectionByLoanId(id);
        CollectionDTO collectionDTO = CollectionMapper.INSTANCE.convertCollectionToCollectionDTO(collection0);

        if(loanService.isUnvalidDueDate(loan.getDueDate())){
            // loan.setArrears(loanService.calculateLoan(loan)); // borç tablosundaki arrears güncellendi
            collectionDTO.setLoanAmount(loanService.calculateLateFeeAmount(loanDTO.getDueDate())); // vadesi geçmiş borçlar için geçikme faizi hesaplandı

            //tahsilat kısmı
            //loan amount kısmı 0lanmalı tahsilat sonucunda
            while(!(collectionDTO.getLoanAmount().equals(BigDecimal.valueOf(0)))){

                collectionDTO.setPrincipalDebtAmount(loan.getPrincipalDebt());

                //normal borç kısmı
                if(!(collectionDTO.getPrincipalDebtAmount().equals(BigDecimal.valueOf(0)))){

                    collectionDTO.setCollectionLoanType(loanNormal);
                    collectionDTO.setLoanId(loan.getLoanId());
                    collectionDTO.setRegistrationDate(LocalDate.now());
                    collectionDTO.setLoanUserId(loan.getUser().getUserId());
                    Collection collection1 = CollectionMapper.INSTANCE.convertCollectionDTOtoCollection(collectionDTO);
                    collectionRepository.save(collection1);
                }
                collectionDTO.setPrincipalDebtAmount(collectionDTO.getLoanAmount());

                //geçikme zammı kısmı
                if(!(collectionDTO.getPrincipalDebtAmount().equals(BigDecimal.valueOf(0)))){

                    collectionDTO.setLoanAmount(BigDecimal.valueOf(0));
                    collectionDTO.setCollectionLoanType(loanLateFee);
                    collectionDTO.setLoanId(loan.getLoanId());
                    collectionDTO.setRegistrationDate(LocalDate.now());
                    collectionDTO.setLoanUserId(loan.getUser().getUserId());
                    Collection collection1 = CollectionMapper.INSTANCE.convertCollectionDTOtoCollection(collectionDTO);
                    collectionRepository.save(collection1);
                }
            }
        }
        return collectionDTO;
    }

    @Override
    public List<CollectionDTO> listCollection(LocalDate firstDate, LocalDate lastDate) {

        List<Collection> collectionList = collectionRepository.findAll();
        List<Collection> newCollectionList = new ArrayList<>();

        for(Collection collection:collectionList){
            if(firstDate.isBefore(collection.getRegistrationDate()) && lastDate.isAfter(collection.getRegistrationDate())){
                newCollectionList.add(collection);
            }
        }
        List<CollectionDTO> collectionDTOList = CollectionMapper.INSTANCE.convertCollectionListToCollectionDTOList(newCollectionList);
        if(collectionDTOList.isEmpty()) throw new CollectionNotFoundException("Collection List is empty");
        return collectionDTOList;
    }

    @Override
    public List<CollectionDTO> listAllCollectionByUserId(Long id) {

        List<Collection> collectionList = collectionRepository.findAll();
        List<Collection> newCollectionList = new ArrayList<>();
        for(Collection collection:collectionList){
            if(collection.getLoanUserId().equals(id)){
                newCollectionList.add(collection);
            }
            else {
                throw new UserNotFoundException("User is not found");
            }
        }
        List<CollectionDTO> collectionDTOList = CollectionMapper.INSTANCE.convertCollectionListToCollectionDTOList(newCollectionList);
        if(collectionDTOList.isEmpty()) throw new CollectionNotFoundException("Collection List is empty");
        return collectionDTOList;
    }

    @Override
    public List<CollectionDTO> listTotalLateFeeAmountByUserId(Long id) {
        List<Collection> collectionList = collectionRepository.findAll();
        List<Collection> newCollectionList = new ArrayList<>();

        for(Collection collection:collectionList){
            if(collection.getLoanUserId().equals(id)){
                if(collection.getCollectionLoanType().equals(loanLateFee)){
                    newCollectionList.add(collection);
                }
            }
            else {
                throw new UserNotFoundException("User is not found");
            }
        }
        List<CollectionDTO> collectionDTOList = CollectionMapper.INSTANCE.convertCollectionListToCollectionDTOList(newCollectionList);
        if(collectionDTOList.isEmpty()) throw new CollectionNotFoundException("Collection List is empty");
        return collectionDTOList;
    }

    @Override
    public BigDecimal calculateTotalLateFee(Long id) {

        List<CollectionDTO> collectionDTOList = listTotalLateFeeAmountByUserId(id);
        BigDecimal total = BigDecimal.valueOf(0);
        for (CollectionDTO collectionDTO:collectionDTOList){
            total = total.add(collectionDTO.getPrincipalDebtAmount());
        }
        return total;
    }


}
