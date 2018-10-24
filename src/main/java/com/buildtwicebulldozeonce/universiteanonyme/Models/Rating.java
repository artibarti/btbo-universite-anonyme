package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Log
@Data
@Entity
@Builder
public class Rating {
    @Id
    private int id;
    @Column(nullable = false)
    private int value;

    @ManyToOne
    private User user;
    @ManyToOne
    private AnonUser anonUser;
}
