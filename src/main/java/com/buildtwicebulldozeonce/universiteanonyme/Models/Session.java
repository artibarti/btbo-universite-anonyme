package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@Log
public class Session {
    @Id
    private int id;
    @Column(nullable = false, unique = true)
    private int counter;
    @Column(nullable = false)
    private boolean isActive;
    @ManyToOne
    private Course course;
}
