package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Log
@Data
@Entity
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int rating;
    @Column(nullable = false)
    private String message;
    @GeneratedValue
    @Column(nullable = false)
    private LocalDateTime timeStamp;
    @Column
    private String answer;

    @ManyToOne
    private Session session;
    @ManyToOne
    private AnonUser anonUser;

    @Transient
    private Set<Rating> ratings;
}
