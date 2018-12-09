package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Set;

@Slf4j
@Data
@Entity
@Builder
public class CourseRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Course course;

    @Transient
    private Set<Comment> comments;
}
