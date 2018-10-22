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
public class Comment {
    @Id
    private int id;
    @ManyToOne
    private AnonUser anonUser;
    @Column(nullable = false)
    private String message;
    //rating
    @ManyToOne
    private User user;
}
