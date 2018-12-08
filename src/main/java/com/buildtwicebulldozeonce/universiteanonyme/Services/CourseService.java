package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseFatDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CoursePulseDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseRatingDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.*;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Log
public class CourseService {

    private final CourseRepository courseRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final CourseSubsRepository courseSubsRepository;
    private final CourseRoomRepository courseRoomRepository;
    private final SessionRepository sessionRepository;
    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final InviteCodeRepository inviteCodeRepository;
    private final AnonUserRepository anonUserRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, RatingRepository ratingRepository,
                         UserRepository userRepository, CourseSubsRepository courseSubsRepository,
                         CourseRoomRepository courseRoomRepository, SessionRepository sessionRepository,
                         CommentRepository commentRepository, QuestionRepository questionRepository,
                         InviteCodeRepository inviteCodeRepository, AnonUserRepository anonUserRepository)
    {
        this.courseRepository = courseRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.courseSubsRepository = courseSubsRepository;
        this.courseRoomRepository = courseRoomRepository;
        this.sessionRepository = sessionRepository;
        this.commentRepository = commentRepository;
        this.questionRepository = questionRepository;
        this.inviteCodeRepository = inviteCodeRepository;
        this.anonUserRepository = anonUserRepository;
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

    public Set<AnonUser> getCourseSubs(int id)
    {
        return this.anonUserRepository.getUsersSubbedForCourse(id);
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

    public List<CoursePulseDTO> getPulseForCourse(int id)
    {
        List<CoursePulseDTO> dailyPulseOnTheLast7Days = new ArrayList<>();
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight).plusDays(1);

        Set<Comment> comments = commentRepository.getCommentsForCourse(id);
        Set<Question> questions = questionRepository.getQuestionsForCourse(id);

        for (int i = 6; i >= 0; i--)
        {
            // make it useable in filtering
            final int static_i = i;

            String day;
            int numberOfComments;
            int numberOfQuestions;

            numberOfComments = (int)
                    comments.stream()
                    .filter(p -> p.getTimestamp().isBefore(todayMidnight.minusDays(static_i)))
                    .filter(p -> p.getTimestamp().isAfter(todayMidnight.minusDays(static_i + 1)))
                    .count();
            numberOfQuestions = (int)
                    questions.stream()
                    .filter(p -> p.getTimestamp().isBefore(todayMidnight.minusDays(static_i)))
                    .filter(p -> p.getTimestamp().isAfter(todayMidnight.minusDays(static_i + 1)))
                    .count();
            day = LocalDate.now().minusDays(static_i).getDayOfWeek().toString().toLowerCase();

            CoursePulseDTO coursePulseDTO = new CoursePulseDTO();

            coursePulseDTO.setDay(day);
            coursePulseDTO.setCommentPulse(numberOfComments);
            coursePulseDTO.setQuestionPulse(numberOfQuestions);

            dailyPulseOnTheLast7Days.add(coursePulseDTO);
        }

        return dailyPulseOnTheLast7Days;
    }

    public Set<InviteCode> getAllInviteCodesForCourse(int courseID)
    {
        return this.inviteCodeRepository.getAllInviteCodesForCourse(courseID);
    }

    public void addInviteCodeForCourse(InviteCode inviteCode)
    {
        this.inviteCodeRepository.save(inviteCode);
    }

    public Set<Course> getHotCourses(int anonUserID)
    {
        return courseRepository.getHotCourses(anonUserID);
    }
}
