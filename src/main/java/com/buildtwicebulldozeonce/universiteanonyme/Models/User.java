package com.buildtwicebulldozeonce.universiteanonyme.Models;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.UserDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.MyGenerator;
import lombok.*;
import lombok.extern.java.Log;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Log
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = MyGenerator.generatorName)
    @GenericGenerator(name = MyGenerator.generatorName, strategy = "myGenerator")
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String doubleHashedPassword;

    @OneToOne
    private Permissions permissions;

    @Transient
    public String token;

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

    public UserDTO convertToDTO()
    {
        return new UserDTO(this.token, this.firstName, this.lastName, this.email);
    }
}