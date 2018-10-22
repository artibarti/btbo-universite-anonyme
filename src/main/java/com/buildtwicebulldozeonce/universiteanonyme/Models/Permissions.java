package com.buildtwicebulldozeonce.universiteanonyme.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Permissions<T> {
    @Id
    private int id;
    @OneToOne
    @Column(nullable = false)
    private T permissionType;
    @Column(nullable = false)
    private double permissionStore;
}
