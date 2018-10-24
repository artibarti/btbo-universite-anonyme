package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Course;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CourseRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return Lists.newArrayList(courseRepository.findAll());
    }

    public Course getCourse(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    public void addCourse(@NonNull Course course) {
        courseRepository.save(course);
    }
}
