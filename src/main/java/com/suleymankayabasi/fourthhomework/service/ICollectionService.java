package com.suleymankayabasi.fourthhomework.service;

import com.suleymankayabasi.fourthhomework.dto.CollectionDTO;

import java.util.Date;
import java.util.List;

public interface ICollectionService {

    CollectionDTO collect(); //???
    List<CollectionDTO> listCollection(Date firstDate, Date lastDate);
    List<CollectionDTO> listAllCollectionByUserId(Long id);
    List<CollectionDTO> listTotalLateFeeAmountByUserId(Long id);
}
