package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

@Data
@NoArgsConstructor
@Log
public class Course {
    private int id;
    private User owner;
    private String inviteCode;
    private String name;

    private List<Session> sessions;
}
