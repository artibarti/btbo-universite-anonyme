package com.buildtwicebulldozeonce.universiteanonyme.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    String token;
    String firstName;
    String lastName;
    String email;
}
