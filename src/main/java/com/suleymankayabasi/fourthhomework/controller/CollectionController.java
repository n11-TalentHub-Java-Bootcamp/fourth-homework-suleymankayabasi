package com.suleymankayabasi.fourthhomework.controller;

import com.suleymankayabasi.fourthhomework.service.ICollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    private ICollectionService collectionService;

    //PUT OR POST KISMINDA KALDIN
}
