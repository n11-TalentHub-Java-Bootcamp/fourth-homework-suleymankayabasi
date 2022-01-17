package com.suleymankayabasi.fourthhomework.service;

import com.suleymankayabasi.fourthhomework.dto.CollectionDTO;
import com.suleymankayabasi.fourthhomework.dto.LoanDTO;
import com.suleymankayabasi.fourthhomework.enums.LoanTypeEnum;
import com.suleymankayabasi.fourthhomework.exception.CollectionNotFoundException;
import com.suleymankayabasi.fourthhomework.exception.LoanNotFoundException;
import com.suleymankayabasi.fourthhomework.mapper.CollectionMapper;
import com.suleymankayabasi.fourthhomework.mapper.LoanMapper;
import com.suleymankayabasi.fourthhomework.model.Collection;
import com.suleymankayabasi.fourthhomework.model.Loan;
import com.suleymankayabasi.fourthhomework.repository.CollectionRepository;
import com.suleymankayabasi.fourthhomework.util.LoanUtils;
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

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private LoanService loanService;

    @Override
    public CollectionDTO collect(Long id,BigDecimal collectionAmount) {

        LoanDTO loanDTO = loanService.findLoanById(id);

        if(loanDTO.equals(null)) throw new LoanNotFoundException("Loan not found.");
        Loan loan = LoanMapper.INSTANCE.convertLoanDTOtoLoan(loanDTO);

        if(LoanUtils.calculateLoan(loan).compareTo(collectionAmount)>0){
            throw new CollectionNotFoundException("Collection amount is not enough!");
        }

        Collection collection = new Collection().builder()
                .principalDebtAmount(loanDTO.getPrincipalDebt())
                        .loanAmount(LoanUtils.calculateLateFeeAmount(loan.getDueDate()))
                                .registrationDate(LocalDate.now())
                                        .collectionLoanType(LoanTypeEnum.STR_NORMAL.toString())
                                                .loanId(loan.getLoanId())
                                                        .loanUserId(id)
                .build();

        collectionRepository.save(collection);

        Collection collection0 = collectionRepository.findCollectionByLoanId(id);
        CollectionDTO collectionDTO = CollectionMapper.INSTANCE.convertCollectionToCollectionDTO(collection0);

        if(LoanUtils.isInvalidDueDate(loan.getDueDate())){

            while(!(collectionDTO.getLoanAmount().equals(BigDecimal.valueOf(0)))){

                if(!(collectionDTO.getLoanAmount().equals( BigDecimal.valueOf(0)))){

                    collectionDTO.setRegistrationDate(LocalDate.now());
                    collectionDTO.setCollectionLoanType(LoanTypeEnum.STR_LATE_FEE.toString());
                    collectionDTO.setLoanId(loan.getLoanId());
                    collectionDTO.setLoanUserId(id);
                    collectionDTO.setPrincipalDebtAmount(collectionDTO.getLoanAmount());
                    collectionDTO.setLoanAmount(BigDecimal.valueOf(0));

                    Collection collection1 = CollectionMapper.INSTANCE.convertCollectionDTOtoCollection(collectionDTO);
                    collectionRepository.save(collection1);
                }
            }
        }

        loanService.updateLoanAmount(id);
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
                if(collection.getCollectionLoanType().equals(LoanTypeEnum.STR_LATE_FEE.toString())){
                    newCollectionList.add(collection);
                }
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
