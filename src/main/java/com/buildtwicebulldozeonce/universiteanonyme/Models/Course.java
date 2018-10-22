package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Log
public class Course {
    @Id
    private int id;
    @ManyToOne
    private User owner;
    @Column(nullable = false, unique = true)
    private String inviteCode;
    @Column(nullable = false)
    private String name;
}
