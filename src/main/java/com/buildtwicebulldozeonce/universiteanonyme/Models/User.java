package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Log
@Data
@Entity
@NoArgsConstructor
public class User {
    @Id
    @Generated
    private int id;
    private String name;
    private String email;
    private String doubleHashedPassword;

    @OneToOne
    private Permissions permissions;
    @OneToMany
    private Set<Admin> adminRoles;
    @OneToMany
    private Set<Comment> comments;
    @OneToMany
    private Set<Course> createdCourses;
    @OneToMany
    private Set<Question> questions;
    @OneToMany
    private Set<Rating> ratings;

    @Transient
    private AnonUser anonUser;

    // picture
}
