package com.buildtwicebulldozeonce.universiteanonyme.Models;

import org.hibernate.annotations.Any;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Permissions {
    @Id
    private int id;
    @OneToOne
    @Column(nullable = false)
    private HasPermission permissionType;
    @Column(nullable = false)
    private double permissionStore;
}
