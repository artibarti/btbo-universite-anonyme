package com.buildtwicebulldozeonce.universiteanonyme.Models;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.SessionSlimDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.Set;

@Log
@Data
@Entity
@Builder
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private int counter;
    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne
    private Course course;

    @Transient
    private Set<Question> questions;
    @Transient
    private Set<Rating> ratings;


    public SessionSlimDTO convertToSlimDTO()
    {
        SessionSlimDTO sessionSlimDTO = new SessionSlimDTO(this.getId(), this.getName());
        return sessionSlimDTO;
    }
}
