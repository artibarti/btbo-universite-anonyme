package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Log
public class User implements HasPermission {
    @Id
    @Generated
    private int id;
    private String name;
    private String email;
    private String doubleHashedPassword;
    @OneToOne
    private Permissions permissions;

    @Transient
    private AnonUser anonym_id;

    // picture
    // permission
}
