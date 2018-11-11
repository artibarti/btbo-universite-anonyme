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
}
