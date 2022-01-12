package com.suleymankayabasi.fourthhomework.repository;

import com.suleymankayabasi.fourthhomework.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection,Long> {
}
