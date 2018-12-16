package com.buildtwicebulldozeonce.universiteanonyme.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CourseRoomCommentDTO {

    int id;
    String message;
    boolean alreadyRatedByUser;
    String name;
    LocalDateTime timeStamp;

}
