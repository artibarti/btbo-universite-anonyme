package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.Set;

@Log
@Data
@Entity
@Builder
public class CourseRoom {
    @Id
    private int id;
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Course course;

    @Transient
    private Set<Comment> comments;
}
