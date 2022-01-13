package com.suleymankayabasi.fourthhomework.repository;

import com.suleymankayabasi.fourthhomework.dto.UserDTO;
import com.suleymankayabasi.fourthhomework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUserId(Long id);

}
