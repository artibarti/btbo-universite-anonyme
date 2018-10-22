package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Log
@Data
@Entity
@NoArgsConstructor
public class AnonUser {
    @Id
    private int id;
    @Column(nullable = false, unique = true)
    private String anonName;
    @Column(nullable = false)
    private String hashedPassword;

    @OneToMany
    private Set<CourseSubs> courses;
    @OneToMany
    private Set<Comment> comments;
    @OneToMany
    private Set<Question> questions;

    @Transient
    private User user;
    //rating
    //picture
}
