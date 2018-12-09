package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;

import javax.persistence.*;

@Log
@Data
@Entity
@Builder
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;
    @ManyToOne
    private Course course;
    @OneToOne
    private Permissions permissions;
}
