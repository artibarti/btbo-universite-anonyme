package com.buildtwicebulldozeonce.universiteanonyme.Models;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseDTO;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public CourseDTO convertToDTO()
    {
        return new CourseDTO(this.id, this.name);
    }
}




