package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.CourseRoom;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CourseRoomRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseRoomService {

    private final CourseRoomRepository courseRoomRepository;

    @Autowired
    public CourseRoomService(CourseRoomRepository courseRoomRepository) {
        this.courseRoomRepository = courseRoomRepository;
    }

    public List<CourseRoom> getAllCourseRooms() {
        return Lists.newArrayList(courseRoomRepository.findAll());
    }

    public CourseRoom getCourseRoom(int id) {
        return courseRoomRepository.findById(id).orElse(null);
    }

    public void addCourseRoom(@NonNull CourseRoom courseRoom) {
        courseRoomRepository.save(courseRoom);
    }
}
