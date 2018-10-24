package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.Set;

@Log
@Data
@Entity
@Builder
@NoArgsConstructor
public class Comment {
    @Id
    private int id;
    @Column(nullable = false)
    private String message;

    @ManyToOne
    private AnonUser anonUser;
    @ManyToOne
    private User user;

    @Transient
    private Set<Rating> ratings;
    //rating
}
