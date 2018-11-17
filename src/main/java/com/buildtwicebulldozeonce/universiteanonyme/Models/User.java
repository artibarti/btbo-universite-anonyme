package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.extern.java.Log;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.Set;

@Log
@Data
@Entity
@Builder
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

    public boolean isValid() {
        StringBuilder errors = new StringBuilder();
        if ("".equals(name))
            errors.append("Invalid username,");
        if ("".equals(email))
            errors.append("invalid email,");
        if ("".equals(doubleHashedPassword))
            errors.append("Missing hashedpassword,");

        if (errors.toString().isEmpty())
        {
            return true;
        }
        else
        {
            log.warning(errors.toString());
            return false;
        }
    }

    //TODO: picture
}
