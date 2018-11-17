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
public class Comment {

    public enum CommentType {CourseRoomComment, QuestionComment}

    @Id
    private int id;
    @Column(nullable = false)
    private String message;

    @ManyToOne
    private AnonUser anonUser;
    @ManyToOne
    private User user;

    @Transient
    private Set<Rating> ratings;

    @Column(nullable = false)
    private int refID;
    @Enumerated(EnumType.STRING)
    private CommentType type;

    public boolean isValid()
    {
        StringBuilder errors = new StringBuilder();
        if ("".equals(message))
        {
            errors.append("Invalid message");
        }

        if (!(user.isValid() || anonUser.isValid()))
        {
            errors.append("Invalid user");
        }

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
