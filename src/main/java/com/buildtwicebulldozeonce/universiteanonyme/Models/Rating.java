package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;

@Log
@Data
@Entity
@Builder
public class Rating {

    public enum RatingType {CommentRating, CourseRating, QuestionRating, SessionRating}

    @Id
    private int id;
    @Column(nullable = false)
    private int value;

    @ManyToOne
    private User user;
    @ManyToOne
    private AnonUser anonUser;
    @Column(nullable = false)
    private int refID;
    @Enumerated(EnumType.STRING)
    private RatingType type;

    public boolean isValid()
    {
        StringBuilder errors = new StringBuilder();
        if (value < 0 || value > 5)
        {
            errors.append("Invalid rating value, ");
        }
        if (!(user.isValid() || anonUser.isValid()))
        {
            errors.append("Invalid user, ");
        }

        if (errors.toString().isEmpty()) {
            return true;
        } else {
            log.warning(errors.toString());
            return false;
        }
    }
}
