package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Data
@Entity
@Builder
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;
    @ManyToOne
    private Course course;
    @OneToOne
    private Permissions permissions;
}
