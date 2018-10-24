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
}




