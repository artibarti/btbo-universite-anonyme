package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.time.LocalDateTime;

@Log
@Data
@Entity
@Builder
public class CourseSubs {
    @Id
    private int id;
    @Column
    private LocalDateTime bannedUntil;

    @OneToOne
    private Course course;
    @OneToOne
    private AnonUser anonUser;

    public boolean isValid()
    {
        StringBuilder errors = new StringBuilder();
        if (!course.isValid())
            errors.append("Invalid course,");
        if (!anonUser.isValid())
            errors.append("Invalid user");

        if (errors.toString().isEmpty()) {
            return true;
        } else {
            log.warning(errors.toString());
            return false;
        }
    }
}
