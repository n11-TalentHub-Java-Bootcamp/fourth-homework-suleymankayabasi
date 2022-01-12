package com.suleymankayabasi.fourthhomework.controller;

import com.suleymankayabasi.fourthhomework.dto.UserDTO;
import com.suleymankayabasi.fourthhomework.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("")
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDTO));
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteByUserId(id);
    }

    @GetMapping
    public List<UserDTO> listAllUser(){
        return userService.findAll();
    }
}
