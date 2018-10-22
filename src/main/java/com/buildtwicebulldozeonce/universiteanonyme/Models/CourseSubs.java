package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Log
public class CourseSubs {
    @Id
    private int id;
    @ManyToOne
    private Course course;
    @ManyToOne
    private AnonUser anonUser;
    private LocalDateTime bannedUntil;
}
