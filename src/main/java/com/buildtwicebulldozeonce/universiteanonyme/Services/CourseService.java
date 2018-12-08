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

    private  static CourseRepository courseRepository;
    private  static RatingRepository ratingRepository;
    private  static UserRepository userRepository;
    private  static CourseSubsRepository courseSubsRepository;
    private  static CourseRoomRepository courseRoomRepository;
    private  static SessionRepository sessionRepository;
    private  static CommentRepository commentRepository;
    private  static QuestionRepository questionRepository;
    private  static InviteCodeRepository inviteCodeRepository;
    private  static AnonUserRepository anonUserRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, RatingRepository ratingRepository,
                         UserRepository userRepository, CourseSubsRepository courseSubsRepository,
                         CourseRoomRepository courseRoomRepository, SessionRepository sessionRepository,
                         CommentRepository commentRepository, QuestionRepository questionRepository,
                         InviteCodeRepository inviteCodeRepository, AnonUserRepository anonUserRepository)
    {
        CourseService.courseRepository = courseRepository;
        CourseService.ratingRepository = ratingRepository;
        CourseService.userRepository = userRepository;
        CourseService.courseSubsRepository = courseSubsRepository;
        CourseService.courseRoomRepository = courseRoomRepository;
        CourseService.sessionRepository = sessionRepository;
        CourseService.commentRepository = commentRepository;
        CourseService.questionRepository = questionRepository;
        CourseService.inviteCodeRepository = inviteCodeRepository;
        CourseService.anonUserRepository = anonUserRepository;
    }

    public static Course getCourse(int id)
    {
        return courseRepository.findById(id).orElse(null);
    }

    public static void addCourse(@NonNull Course course)
    {
        courseRepository.save(course);
    }

    public static void updateCourse(Course course)
    {
        if (courseRepository.existsById(course.getId()))
            courseRepository.save(course);
    }

    private static void deleteCourseRooms(int id)
    {
    }

    public static void deleteCourse(int id)
    {
       commentRepository.getCommentsForCourse(id).forEach(commentRepository::delete);

       courseRepository.findById(id).get().getCourseRooms().forEach(courseRoomRepository::delete);

        courseRepository.deleteById(id);
    }

    public static Set<User> getCourseAdmins(int id)
    {
        return userRepository.getCourseAdmins(id);
    }

    public static Set<AnonUser> getCourseSubs(int id)
    {
        return anonUserRepository.getUsersSubbedForCourse(id);
    }

    public static Set<CourseRoom> getCourseRooms(int id)
    {
        return courseRoomRepository.getCourseRoomsForCourse(id);
    }

    public static Set<Session> getSessionsForCourse(int id)
    {
        return sessionRepository.getSessionsForCourse(id);
    }

    public static Set<Rating> getRatingsForCourse(int id)
    {
        return ratingRepository.getRatingsByTypeAndID(id, Rating.RatingType.CourseRating);
    }

    public static CourseRatingDTO getRatingSumForCourse(int id)
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

    public static List<CoursePulseDTO> getPulseForCourse(int id)
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

    public static Set<InviteCode> getAllInviteCodesForCourse(int courseID)
    {
        return inviteCodeRepository.getAllInviteCodesForCourse(courseID);
    }

    public static void addInviteCodeForCourse(InviteCode inviteCode)
    {
        inviteCodeRepository.save(inviteCode);
    }

    public static Set<Course> getHotCourses()
    {
        return courseRepository.getHotCourses(1);
    }
}
