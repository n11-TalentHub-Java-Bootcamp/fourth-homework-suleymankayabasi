package com.suleymankayabasi.fourthhomework.mapper;

import com.suleymankayabasi.fourthhomework.dto.LoanDTO;
import com.suleymankayabasi.fourthhomework.model.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanMapper {

    LoanMapper INSTANCE  = Mappers.getMapper(LoanMapper.class);

    @Mapping(source = "userId",target="user.userId")
    Loan convertLoanDTOtoLoan(LoanDTO loanDTO);

    @Mapping(source = "user.userId", target = "userId")
    LoanDTO convertLoanToLoanDTO(Loan loan);

    List<Loan> convertLoanDTOListToLoanList(List<LoanDTO> loanDTOList);

    List<LoanDTO> convertLoanListToLoanDTOList(List<Loan> loanList);

}
