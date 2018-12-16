package com.buildtwicebulldozeonce.universiteanonyme.DTOs;

import com.buildtwicebulldozeonce.universiteanonyme.Models.CourseRoom;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseRoomDTO {

    public CourseRoomDTO(CourseRoom courseRoom) {
        this.id = courseRoom.getId();
        this.name = courseRoom.getName();
    }

    int id;
    String name;
}
