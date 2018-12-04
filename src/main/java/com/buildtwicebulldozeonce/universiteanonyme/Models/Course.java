package com.buildtwicebulldozeonce.universiteanonyme.Models;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseFatDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseSlimDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.Set;

@Log
@Data
@Entity
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private boolean hided;

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


    public CourseSlimDTO convertToSlimDTO()
    {
        return new CourseSlimDTO(this.id, this.name);
    }

    public CourseFatDTO convertToFatDTO()
    {
        return new CourseFatDTO(this.id, this.name, this.description, this.hided);
    }
}




