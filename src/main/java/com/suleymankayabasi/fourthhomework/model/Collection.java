package com.suleymankayabasi.fourthhomework.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name="COLLECTIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Collection {

    @Id
    private Long id;
}
