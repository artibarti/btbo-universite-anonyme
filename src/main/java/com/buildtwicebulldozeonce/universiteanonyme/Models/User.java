package com.buildtwicebulldozeonce.universiteanonyme.Models;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.UserDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.IdGenerator;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Slf4j
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(generator = IdGenerator.generatorName)
    @GenericGenerator(name = IdGenerator.generatorName, strategy = "com.buildtwicebulldozeonce.universiteanonyme.Helpers.IdGenerator")
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String doubleHashedPassword;
    @OneToOne
    private Permissions permissions;
    @Transient
    private Set<Admin> adminRoles;
    @Transient
    private Set<Comment> comments;
    @Transient
    private Set<Course> createdCourses;
    @Transient
    private Set<Question> questions;
    @Transient
    private Set<Rating> ratings;
    @Transient
    private AnonUser anonUser;
}