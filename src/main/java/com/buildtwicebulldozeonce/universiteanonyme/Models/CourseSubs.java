package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.time.LocalDateTime;

@Log
@Data
@Entity
@Builder
public class CourseSubs {
    @Id
    private int id;
    @Column
    private LocalDateTime bannedUntil;

    @OneToOne
    private Course course;
    @OneToOne
    private AnonUser anonUser;
}
