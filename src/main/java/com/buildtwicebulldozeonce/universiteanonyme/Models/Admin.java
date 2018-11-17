package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Log
@Data
@Entity
@Builder
public class Admin {
    @Id
    private int id;

    @ManyToOne
    private User user;
    @ManyToOne
    private Course course;
    @OneToOne
    private Permissions permissions;

    public boolean isValid() {
        StringBuilder errors = new StringBuilder();
        if (!user.isValid())
            errors.append("Invalid user,");
        if (!course.isValid())
            errors.append("invalid course,");

        if (errors.toString().isEmpty())
        {
            return true;
        }
        else
        {
            log.warning(errors.toString());
            return false;
        }
    }
}
