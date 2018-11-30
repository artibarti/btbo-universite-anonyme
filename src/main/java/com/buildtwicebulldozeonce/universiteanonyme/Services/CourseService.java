package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseRatingDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.*;
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
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final CourseSubsRepository courseSubsRepository;
    private final CourseRoomRepository courseRoomRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, RatingRepository ratingRepository,
                         UserRepository userRepository, CourseSubsRepository courseSubsRepository,
                         CourseRoomRepository courseRoomRepository, SessionRepository sessionRepository)
    {
        this.courseRepository = courseRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.courseSubsRepository = courseSubsRepository;
        this.courseRoomRepository = courseRoomRepository;
        this.sessionRepository = sessionRepository;
    }

    public Course getCourse(int id)
    {
        return courseRepository.findById(id).orElse(null);
    }

    public void addCourse(@NonNull Course course)
    {
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

    public Set<User> getCourseAdmins(int id)
    {
        return userRepository.getCourseAdmins(id);
    }

    public Set<CourseSubs> getCourseSubs(int id)
    {
        return courseSubsRepository.getCourseSubsForCourse(id);
    }

    public Set<CourseRoom> getCourseRooms(int id)
    {
        return courseRoomRepository.getCourseRoomsForCourse(id);
    }

    public Set<Session> getSessionsForCourse(int id)
    {
        return sessionRepository.getSessionsForCourse(id);
    }

    public Set<Rating> getRatingsForCourse(int id)
    {
        return ratingRepository.getRatingsByTypeAndID(id, Rating.RatingType.CourseRating);
    }

    public CourseRatingDTO getRatingSumForCourse(int id)
    {
        Set<Rating> ratings = ratingRepository.getRatingsByTypeAndID(id, Rating.RatingType.CourseRating);
        CourseRatingDTO courseRatingDTO = new CourseRatingDTO();
        courseRatingDTO.setNumberOfRatings(ratings.size());
        if (ratings.size() != 0)
            courseRatingDTO.setSum(ratings.stream().mapToInt(Rating::getValue).sum() / ratings.size());
        else
            courseRatingDTO.setSum(0);

        return courseRatingDTO;
    }

}
