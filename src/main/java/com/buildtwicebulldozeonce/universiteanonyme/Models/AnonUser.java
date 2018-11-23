package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Log
@Data
@Entity
@Builder
public class AnonUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String anonName;
    @Column(nullable = false)
    private String hashedPassword;

    @Transient
    private Set<CourseSubs> courses;
    @Transient
    private Set<Comment> comments;
    @Transient
    private Set<Question> questions;
    @Transient
    private User user;
    //rating
    //picture
}
