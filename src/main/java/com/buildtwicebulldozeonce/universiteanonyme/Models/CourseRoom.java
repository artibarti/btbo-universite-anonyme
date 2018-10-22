package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Log
public class CourseRoom {
    @Id
    private int id;
    @OneToOne
    private Course course;
    @Column(nullable = false)
    private String name;
}
