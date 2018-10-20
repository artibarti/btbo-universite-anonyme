package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@Log
public class User {
    @Id
    @Generated
    private int id;
    @Column
    private String email;
    @Column
    private String doubleHashedPassword;
    @Column
    private int anonym_id;
    @Column
    private String name;
    // picture
    // permission




}
