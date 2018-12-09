package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Slf4j
@Data
@Entity
@Builder
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(nullable = false)
    private LocalDateTime timestamp;
    public enum RatingType {CommentRating, CourseRating, QuestionRating, SessionRating}

}
