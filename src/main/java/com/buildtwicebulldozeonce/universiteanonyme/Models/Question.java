package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
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

    public boolean isValid() {
        StringBuilder errors = new StringBuilder();
        if (!anonUser.isValid())
            errors.append("Invalid user,");
        if ("".equals(answer))
            errors.append("Invalid answer,");
        if (!session.isValid())
            errors.append("Invalid session");

            if (errors.toString().isEmpty()) {
                return true;
            } else {
                log.warning(errors.toString());
                return false;
            }
    }
}
