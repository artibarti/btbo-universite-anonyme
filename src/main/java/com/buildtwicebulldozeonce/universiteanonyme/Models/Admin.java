package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Log
@Data
@Entity
@NoArgsConstructor
public class Admin {
    @Id
    private int id;

    @ManyToOne
    private User user;
    @ManyToOne
    private Course course;
    @OneToOne
    private Permissions permissions;
}
