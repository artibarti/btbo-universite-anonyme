package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Log
public class CourseSubs {
    private Course course;
    private AnonUser anonUser;
    private LocalDateTime bannedUntil;

}
