package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.Set;

@Log
@Data
@Entity
@NoArgsConstructor
public class CourseRoom {
    @Id
    private int id;
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Course course;
    @OneToMany
    private Set<Comment> comments;
}
