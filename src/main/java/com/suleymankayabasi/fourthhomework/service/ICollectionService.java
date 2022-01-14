package com.suleymankayabasi.fourthhomework.service;

import com.suleymankayabasi.fourthhomework.dto.CollectionDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ICollectionService {

    CollectionDTO collect(Long id, BigDecimal collectionAmount); //???
    List<CollectionDTO> listCollection(LocalDate firstDate, LocalDate lastDate);
    List<CollectionDTO> listAllCollectionByUserId(Long id);
    List<CollectionDTO> listTotalLateFeeAmountByUserId(Long id);
}
