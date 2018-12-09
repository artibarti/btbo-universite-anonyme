package com.buildtwicebulldozeonce.universiteanonyme.Models;

import com.buildtwicebulldozeonce.universiteanonyme.Helpers.IdGenerator;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Slf4j
@Data
@Entity
@Builder
public class AnonUser {
    @Id
    @GeneratedValue(generator = IdGenerator.generatorName)
    @GenericGenerator(name = IdGenerator.generatorName, strategy = "com.buildtwicebulldozeonce.universiteanonyme.Helpers.IdGenerator")
    private int id;
    @Column(nullable = false, unique = true)
    private String anonName;
    @Column(nullable = false)
    private String hashedPassword;

    @Transient
    private Set<CourseSubs> courses;
    @Transient
    private Set<Comment> comments;
    @Transient
    private Set<Question> questions;
    @Transient
    private User user;
    //rating
    //picture
}
