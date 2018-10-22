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
public class Course {
    @Id
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String inviteCode;

    @ManyToOne
    private User owner;
    @OneToMany
    private Set<Admin> admins;
    @OneToMany
    private Set<CourseSubs> courseSubs;
    @OneToMany
    private Set<CourseRoom> courseRooms;
    @OneToMany
    private Set<Rating> ratings;
    @OneToMany
    private Set<Session> sessions;
}




