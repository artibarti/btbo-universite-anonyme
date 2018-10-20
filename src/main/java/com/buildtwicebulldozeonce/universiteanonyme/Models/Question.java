package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Log
public class Question {
    private Session session;
    private int id;
    private LocalDateTime timeStamp;
    private AnonUser anonUser;
    private int rating;
    private String message;
}
