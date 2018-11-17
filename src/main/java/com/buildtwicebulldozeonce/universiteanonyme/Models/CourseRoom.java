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

    public boolean isValid() {
        StringBuilder errors = new StringBuilder();
        if (!course.isValid())
            errors.append("Invalid course,");
        if ("".equals(name))
            errors.append("Invalid courseRoomName,");

        if (errors.toString().isEmpty()) {
            return true;
        } else {
            log.warning(errors.toString());
            return false;
        }
    }
}
