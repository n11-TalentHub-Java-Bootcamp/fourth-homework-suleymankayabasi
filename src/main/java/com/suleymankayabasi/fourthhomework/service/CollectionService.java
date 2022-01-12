package com.suleymankayabasi.fourthhomework.service;

import com.suleymankayabasi.fourthhomework.dto.CollectionDTO;
import com.suleymankayabasi.fourthhomework.repository.CollectionRepository;
import com.suleymankayabasi.fourthhomework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CollectionService implements ICollectionService{

    @Autowired
    private CollectionRepository collectionRepository;

    @Override
    public CollectionDTO collect() {
        return null;
    }

    @Override
    public List<CollectionDTO> listCollection(Date firstDate, Date lastDate) {
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
