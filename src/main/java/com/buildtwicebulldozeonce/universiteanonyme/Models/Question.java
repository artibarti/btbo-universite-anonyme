package com.buildtwicebulldozeonce.universiteanonyme.Models;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.QuestionSlimDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Log
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


    public QuestionSlimDTO convertToSlimDTO()
    {
        QuestionSlimDTO questionSlimDTO =
                new QuestionSlimDTO(this.getId(), this.getMessage(), this.getTimestamp());

        return questionSlimDTO;
    }
}
