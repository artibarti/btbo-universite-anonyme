package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Data
@NoArgsConstructor
@Log
public class User {

    private int id;
    private String email;
    private String doubleHashedPassword;
    private int anonym_id;
    private String name;
    // picture
    // permission




}
