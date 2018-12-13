package com.buildtwicebulldozeonce.universiteanonyme.Models;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LoggedInUser {

    private User user;
    private AnonUser anonUser;
    private String token;
    private LocalDateTime validUntil;
}
