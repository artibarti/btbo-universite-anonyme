package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CourseRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public void updateCourse(Course course)
    {
        if (courseRepository.existsById(course.getId()))
            courseRepository.save(course);
    }

    public void deleteCourse(int id)
    {
        courseRepository.deleteById(id);
    }

    public Set<Admin> getCourseAdmins(int id)
    {
        return courseRepository.getCourseAdmins(id);
    }

    public Set<CourseSubs> getCourseSubs(int id)
    {
        return courseRepository.getCourseSubs(id);
    }

    public Set<CourseRoom> getCourseRooms(int id)
    {
        return courseRepository.getCourseRooms(id);
    }

    public Set<Session> getSessionsForCourse(int id)
    {
        return courseRepository.getSessionsForCourse(id);
    }

    // TODO: getRatingsForCourse


}
