package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.hibernate.annotations.Any;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Log
@Data
@Entity
@NoArgsConstructor
public class Permissions {
    @Id
    private int id;
    @Column(nullable = false)
    private double permissionStore;
}
