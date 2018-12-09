package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Data
@Entity
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(nullable = false)
    private LocalDateTime timestamp;
    public enum CommentType {CourseRoomComment, QuestionComment}

}
