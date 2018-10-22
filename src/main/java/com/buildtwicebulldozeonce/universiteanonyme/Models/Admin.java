package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
@Log
public class Admin {
    @Id
    private int id;
    @OneToOne
    private User user;
    @OneToOne
    private Course course;
    @OneToOne
    private Permissions permissions;
}
