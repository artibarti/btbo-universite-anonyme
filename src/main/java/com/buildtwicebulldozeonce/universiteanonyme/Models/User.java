package com.buildtwicebulldozeonce.universiteanonyme.Models;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.UserDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.MyGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @Transient
    public String token;
    @Id
    @GeneratedValue(generator = MyGenerator.generatorName)
    @GenericGenerator(name = MyGenerator.generatorName, strategy = "com.buildtwicebulldozeonce.universiteanonyme.Helpers.MyGenerator")
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

    public UserDTO convertToDTO() {
        return new UserDTO(this.token, this.firstName, this.lastName, this.email);
    }
}