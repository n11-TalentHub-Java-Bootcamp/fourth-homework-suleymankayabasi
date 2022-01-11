package com.suleymankayabasi.fourthhomework.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="APP_USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID",nullable = false)
    private Long userId;

    @Column(length = 50,name = "NAME",nullable = false)
    private String name;

    @Column(length = 50,name = "LAST_NAME",nullable = false)
    private String lastName;

    @Column(length = 20,name = "ID_NUMBER",unique = true,nullable = false)
    private Long identificationNumber;

}
