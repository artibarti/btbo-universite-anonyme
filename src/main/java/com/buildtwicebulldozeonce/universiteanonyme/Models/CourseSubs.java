package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Log
public class CourseSubs {
    @ManyToOne
    private Course course;
    @OneToOne
    private AnonUser anonUser;
    @Column(nullable = true)
    private LocalDateTime bannedUntil;

}
