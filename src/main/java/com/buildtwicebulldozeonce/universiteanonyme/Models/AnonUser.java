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
public class AnonUser {
    @Id
    private int id;
    @Column(nullable = false, unique = true)
    private String anonName;
    @Column(nullable = false)
    private String hashedPassword;
    @OneToMany
    private List<CourseSubs> courses;
    //rating
    //picture
}
