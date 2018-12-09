package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;

import javax.persistence.*;

@Log
@Data
@Entity
@Builder
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private double permissionStore;
}
