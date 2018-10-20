package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Data
@NoArgsConstructor
@Log
public class Comment {
    private int id;
    private AnonUser anonUser;
    private String message;
    //rating
    private User user;
}
