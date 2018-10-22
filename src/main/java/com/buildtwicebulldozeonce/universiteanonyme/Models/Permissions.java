package com.buildtwicebulldozeonce.universiteanonyme.Models;

import org.hibernate.annotations.Any;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Permissions {
    @Id
    private int id;
    @Column(nullable = false)
    private String permissionType;
    @Column(nullable = false)
    private int permissionForeignKey;
    @Column(nullable = false)
    private double permissionStore;
}
