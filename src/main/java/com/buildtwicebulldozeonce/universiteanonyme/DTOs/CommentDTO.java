package com.buildtwicebulldozeonce.universiteanonyme.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO
{
    int id;
    String message;
    String name;
    LocalDateTime timestamp;
}
