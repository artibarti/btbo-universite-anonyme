package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.Set;

@Log
@Data
@Entity
@Builder
public class Course {
    @Id
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String inviteCode;

    @ManyToOne
    private User owner;

    @Transient
    private Set<Admin> admins;
    @Transient
    private Set<CourseSubs> courseSubs;
    @Transient
    private Set<CourseRoom> courseRooms;
    @Transient
    private Set<Rating> ratings;
    @Transient
    private Set<Session> sessions;

    public boolean isValid() {
        StringBuilder errors = new StringBuilder();
        if (!owner.isValid())
            errors.append("Invalid owner,");
        if ("".equals(name))
            errors.append("Invalid coursename,");
        if ("".equals(inviteCode))
            errors.append("Invalid invitecode,");

        if (errors.toString().isEmpty()) {
            return true;
        } else {
            log.warning(errors.toString());
            return false;
        }
    }
}




