package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Log
public class Question {
    @Id
    private int id;
    @OneToOne
    private Session session;
    @GeneratedValue
    @Column(nullable = false)
    private LocalDateTime timeStamp;
    @OneToOne
    private AnonUser anonUser;
    @Column
    private int rating;
    @Column(nullable = false)
    private String message;
}
