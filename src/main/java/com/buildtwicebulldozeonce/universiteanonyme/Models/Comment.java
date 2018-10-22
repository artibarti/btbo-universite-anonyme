package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Log
public class Comment {
    @Id
    private int id;
    @OneToOne
    private AnonUser anonUser;
    @Column(nullable = false)
    private String message;
    //rating
    @OneToOne
    private User user;
}
