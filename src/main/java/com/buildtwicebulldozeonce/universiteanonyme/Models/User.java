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

    @Transient
    private Set<Admin> adminRoles;
    @Transient
    private Set<Comment> comments;
    @Transient
    private Set<Course> createdCourses;
    @Transient
    private Set<Question> questions;
    @Transient
    private Set<Rating> ratings;
    @Transient
    private AnonUser anonUser;

    //TODO: picture
}
