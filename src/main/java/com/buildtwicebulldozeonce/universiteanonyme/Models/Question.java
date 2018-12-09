package com.buildtwicebulldozeonce.universiteanonyme.Models;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.QuestionSlimDTO;
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
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int rating;
    @Column(nullable = false)
    private String message;
    @GeneratedValue
    @Column(nullable = false)
    private LocalDateTime timestamp;
    @Column
    private String answer;

    @ManyToOne
    private Session session;
    @ManyToOne
    private AnonUser anonUser;

    @Transient
    private Set<Rating> ratings;


    public QuestionSlimDTO convertToSlimDTO() {
        QuestionSlimDTO questionSlimDTO =
                new QuestionSlimDTO(this.getId(), this.getMessage(), this.getTimestamp());

        return questionSlimDTO;
    }
}
