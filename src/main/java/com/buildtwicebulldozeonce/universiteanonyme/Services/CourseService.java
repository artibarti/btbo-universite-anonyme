package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CoursePulseDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseRatingDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.SessionSlimDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@Slf4j
public class CourseService {

    private static CourseRepository courseRepository;
    private static RatingRepository ratingRepository;
    private static UserRepository userRepository;
    private static CourseSubsRepository courseSubsRepository;
    private static CourseRoomRepository courseRoomRepository;
    private static SessionRepository sessionRepository;
    private static CommentRepository commentRepository;
    private static QuestionRepository questionRepository;
    private static InviteCodeRepository inviteCodeRepository;
    private static AnonUserRepository anonUserRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, RatingRepository ratingRepository,
                         UserRepository userRepository, CourseSubsRepository courseSubsRepository,
                         CourseRoomRepository courseRoomRepository, SessionRepository sessionRepository,
                         CommentRepository commentRepository, QuestionRepository questionRepository,
                         InviteCodeRepository inviteCodeRepository, AnonUserRepository anonUserRepository) {
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

    public static Course getCourse(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    public static void addCourse(@NonNull Course course) {
        log.trace("Course added with id: " + course.getId());
        courseRepository.save(course);
    }

    public static void updateCourse(Course course) {
        if (courseRepository.existsById(course.getId()))
            courseRepository.save(course);
    }

    public static void deleteCourse(Course course) {

        log.info(String.format("Cleaning up dependencies for course: %s...", course.getId()));

        log.info("Deleting sessions related to the course");
        sessionRepository.getSessionsByCourse_Id(course.getId()).forEach(SessionService::deleteSession);

        log.info("Deleting courseRooms related to the course");
        courseRoomRepository.getCourseRoomsByCourse_Id(course.getId()).forEach(CourseRoomService::deleteCourseRoom);

        log.info("Deleting courseSubs related to the course");
        courseSubsRepository.getCourseSubsByCourse_Id(course.getId()).forEach(courseSubsRepository::delete);

        log.info("Deleting course");
        courseRepository.delete(course);
    }

    public static void leaveCourse(int id, String token) {
        Triplet<String, User, AnonUser> loggedInUser = UserService.getLoggedInUser(token);

        log.info("User with id: " + loggedInUser.getValue1().getId() + " leaving course");
        if (loggedInUser.getValue1().getId() == CourseService.getCourse(id).getOwner().getId()) {
            log.info("The user is the owner of the course");
            return;
        }

        courseSubsRepository.delete(courseSubsRepository.getByCourse_IdAndAndAnonUser_Id(id, loggedInUser.getValue2().getId()));
        log.info("Successfully left the course.");
    }

    public static Set<User> getCourseAdmins(int id) {
        return userRepository.getCourseAdmins(id);
    }

    public static Set<AnonUser> getCourseSubs(int id) {
        return anonUserRepository.getUsersSubbedForCourse(id);
    }

    public static Set<CourseRoom> getCourseRooms(int id) {
        return courseRoomRepository.getCourseRoomsForCourse(id);
    }

    public static Set<Session> getSessionsForCourse(int id) {
        return sessionRepository.getSessionsForCourse(id);
    }

    public static Set<Rating> getRatingsForCourse(int id) {
        return ratingRepository.getRatingsByTypeAndID(id, Rating.RatingType.CourseRating);
    }

    public static CourseRatingDTO getRatingSumForCourse(int id) {
        Set<Rating> ratings = ratingRepository.getRatingsByTypeAndID(id, Rating.RatingType.CourseRating);
        CourseRatingDTO courseRatingDTO = new CourseRatingDTO();
        courseRatingDTO.setNumberOfRatings(ratings.size());
        if (ratings.size() != 0)
            courseRatingDTO.setSum(ratings.stream().mapToInt(Rating::getValue).sum() / ratings.size());
        else
            courseRatingDTO.setSum(0);

        return courseRatingDTO;
    }

    public static List<CoursePulseDTO> getPulseForCourse(int id) {
        List<CoursePulseDTO> dailyPulseOnTheLast7Days = new ArrayList<>();
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight).plusDays(1);

        Set<Comment> comments = commentRepository.getCommentsForCourse(id);
        Set<Question> questions = questionRepository.getQuestionsForCourse(id);

        for (int i = 6; i >= 0; i--) {
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

    public static Set<InviteCode> getAllInviteCodesForCourse(int courseID, String token) {

        if (UserService.checkIfUserOwnsCourse(courseID, token)) {
            return inviteCodeRepository.getAllInviteCodesForCourse(courseID);
        } else {
            return new HashSet<>();
        }
    }

    public static void addInviteCodeForCourse(InviteCode inviteCode) {
        inviteCodeRepository.save(inviteCode);
    }

    public static Set<Course> getHotCourses(int anonUserID) {
        return courseRepository.getHotCourses(anonUserID);
    }

    public static boolean isThereAnActiveSessionForCourse(Course course)
    {
        return sessionRepository.getSessionsForCourse(course.getId()).stream().anyMatch(Session::isActive);
    }

    public static SessionSlimDTO getActiveSession(Course course) {

        Optional<Session> sessionOptional = sessionRepository.getSessionsForCourse(course.getId()).stream().filter(Session::isActive).findFirst();

        return sessionOptional.map(Session::convertToSlimDTO).orElse(null);
    }
}
