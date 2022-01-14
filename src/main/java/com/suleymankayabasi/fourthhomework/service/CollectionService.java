package com.suleymankayabasi.fourthhomework.service;

import com.suleymankayabasi.fourthhomework.dto.CollectionDTO;
import com.suleymankayabasi.fourthhomework.dto.LoanDTO;
import com.suleymankayabasi.fourthhomework.exception.CollectionNotFoundException;
import com.suleymankayabasi.fourthhomework.exception.LoanNotFoundException;
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
import java.util.List;

@Service
@Transactional
public class CollectionService implements ICollectionService{

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

        Collection collection = collectionRepository.findCollectionByLoanId(id);
        CollectionDTO collectionDTO = CollectionMapper.INSTANCE.convertCollectionToCollectionDTO(collection);

        if(loanService.calculateLoan(loan).compareTo(collectionAmount)>0){
            throw new CollectionNotFoundException("Collection amount is not enough!");
        }

        if(loanService.isUnvalidDueDate(loan.getDueDate())){
            loan.setArrears(loanService.calculateLoan(loan)); // borç tablosundaki arrears güncellendi
            collectionDTO.setLateFeeAmount(loanService.calculateLateFeeAmount(LocalDate.now())); // vadesi geçmiş borçlar için geçikme faizi hesaplandı

            //tahsilat kısmı
            //loan amount kısmı 0lanmalı tahsilat sonucunda
            while(!(collectionDTO.getLoanAmount().equals(BigDecimal.valueOf(0)))){

                collectionDTO.setPrincipalDebtAmount(loan.getPrincipalDebt());
                //normal borç kısmı
                if(!(collectionDTO.getPrincipalDebtAmount().equals(BigDecimal.valueOf(0)))){

                    collectionDTO.setLoanAmount(loan.getPrincipalDebt());
                    collectionDTO.setColectionLoanType(loanNormal);
                    collectionDTO.setLoanId(loan.getLoanId());
                    collectionDTO.setLoanUserId(loan.getUser().getUserId());
                    Collection collection1 = CollectionMapper.INSTANCE.convertCollectionDTOtoCollection(collectionDTO);
                    collectionRepository.save(collection1);
                }
                collectionDTO.setPrincipalDebtAmount(BigDecimal.valueOf(0));

                collectionDTO.setPrincipalDebtAmount(collectionDTO.getLateFeeAmount());

                //geçikme zammı kısmı
                if(!(collectionDTO.getPrincipalDebtAmount().equals(BigDecimal.valueOf(0)))){

                    collectionDTO.setLoanAmount(BigDecimal.valueOf(0));
                    collectionDTO.setColectionLoanType(loanLateFee);
                    collectionDTO.setLoanId(loan.getLoanId());
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
        return null;
    }

    @Override
    public List<CollectionDTO> listAllCollectionByUserId(Long id) {
        return null;
    }

    @Override
    public List<CollectionDTO> listTotalLateFeeAmountByUserId(Long id) {
        return null;
    }
}
