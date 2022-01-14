package com.suleymankayabasi.fourthhomework.controller;

import com.suleymankayabasi.fourthhomework.dto.CollectionDTO;
import com.suleymankayabasi.fourthhomework.service.ICollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    private ICollectionService collectionService;

    @PutMapping("/{id}/{collectionAmount}")
    public ResponseEntity<CollectionDTO> collect(@PathVariable  Long id, @PathVariable  BigDecimal collectionAmount){
        return ResponseEntity.status(HttpStatus.CREATED).body(collectionService.collect(id,collectionAmount));
    }

    @GetMapping
    public List<CollectionDTO> findAllCollectionByDate(@RequestParam(name= "dateBefore", defaultValue="")String dateBeforeString,
                                       @RequestParam(name= "dateAfter", defaultValue="")String dateAfterString) {
        String pattern = "yyyy-MM-dd";
        LocalDate dateBefore = LocalDate.parse(dateBeforeString, DateTimeFormatter.ofPattern(pattern));
        LocalDate dateAfter = LocalDate.parse(dateAfterString, DateTimeFormatter.ofPattern(pattern));
        return collectionService.listCollection(dateBefore, dateAfter);
    }

    @GetMapping("/user-id/{id}")
    public List<CollectionDTO> findAllCollectionByUserId(@PathVariable Long id){
        return collectionService.listAllCollectionByUserId(id);
    }

    @GetMapping("/late-fee-amount/user-id/{id}")
    public List<CollectionDTO> findAllLateFeeAmountByUserId(@PathVariable Long id){
        return collectionService.listTotalLateFeeAmountByUserId(id);
    }

    @GetMapping("/total-late-fee/{id}")
    public ResponseEntity<BigDecimal> calculateTotalLateFee(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(collectionService.calculateTotalLateFee(id));
    }

}
