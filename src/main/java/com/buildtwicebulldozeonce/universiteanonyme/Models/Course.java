package com.buildtwicebulldozeonce.universiteanonyme.Models;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseFatDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseSlimDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Set;

@Slf4j
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
    private boolean hidden;

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


    public CourseSlimDTO convertToSlimDTO() {
        return new CourseSlimDTO(this.id, this.name, this.hidden);
    }

    public CourseFatDTO convertToFatDTO() {
        return new CourseFatDTO(this.id, this.name, this.description, this.hidden);
    }
}




