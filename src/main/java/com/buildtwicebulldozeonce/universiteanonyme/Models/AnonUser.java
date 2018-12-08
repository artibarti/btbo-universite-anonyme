package com.buildtwicebulldozeonce.universiteanonyme.Models;

import com.buildtwicebulldozeonce.universiteanonyme.Helpers.MyGenerator;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Log
@Data
@Entity
@Builder
public class AnonUser {
    @Id
    @GeneratedValue(generator = MyGenerator.generatorName)
    @GenericGenerator(name = MyGenerator.generatorName, strategy = "com.buildtwicebulldozeonce.universiteanonyme.Helpers.MyGenerator")
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
