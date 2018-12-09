package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Slf4j
@Data
@Entity
@NoArgsConstructor
public class CourseSubs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private LocalDateTime bannedUntil;

    @OneToOne
    private Course course;
    @OneToOne
    private AnonUser anonUser;
}
