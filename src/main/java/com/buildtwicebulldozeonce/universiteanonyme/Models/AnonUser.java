package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Data
@NoArgsConstructor
@Log
public class AnonUser {
    private int id;
    private String anonName;
    private String hashedPassword;
    //rating
    //picture
}
