package com.buildtwicebulldozeonce.universiteanonyme.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class QuestionFatDTO {
    int id;
    String message;
    LocalDateTime timestamp;
    String name;
}
