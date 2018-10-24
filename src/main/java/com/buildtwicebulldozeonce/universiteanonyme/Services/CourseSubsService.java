package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.CourseSubs;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CourseSubsRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseSubsService {

    private final CourseSubsRepository courseSubsRepository;

    @Autowired
    public CourseSubsService(CourseSubsRepository courseSubsRepository) {
        this.courseSubsRepository = courseSubsRepository;
    }

    public List<CourseSubs> getAllCourseSubs() {
        return Lists.newArrayList(courseSubsRepository.findAll());
    }

    public CourseSubs getCourseSubs(int id) {
        return courseSubsRepository.findById(id).orElse(null);
    }

    public void addCourseSubs(@NonNull CourseSubs courseSubs) {
        courseSubsRepository.save(courseSubs);
    }
}
