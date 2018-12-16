package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.UserDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.PasswordHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.TokenHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserService {

    private static UserRepository userRepository;
    private static AnonUserRepository anonUserRepository;
    private static CourseRepository courseRepository;
    private static RatingRepository ratingRepository;
    private static InviteCodeRepository inviteCodeRepository;
    private static CourseSubsRepository courseSubsRepository;
    private static CommentRepository commentRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       AnonUserRepository anonUserRepository,
                       CourseRepository courseRepository,
                       RatingRepository ratingRepository,
                       InviteCodeRepository inviteCodeRepository,
                       CourseSubsRepository courseSubsRepository,
                       CommentRepository commentRepository) {

        UserService.userRepository = userRepository;
        UserService.anonUserRepository = anonUserRepository;
        UserService.courseRepository = courseRepository;
        UserService.ratingRepository = ratingRepository;
        UserService.inviteCodeRepository = inviteCodeRepository;
        UserService.courseSubsRepository = courseSubsRepository;
        UserService.commentRepository = commentRepository;
    }

    public static User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public static void addUser(@NonNull User user) {
        userRepository.save(user);
    }

    public static void addAnonUser(@NonNull AnonUser anonUser) {
        anonUserRepository.save(anonUser);
    }

    public static void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public static Set<Course> getCoursesAdminedByUser(String token) {
        return courseRepository.getCoursesAdminedByUser(LoggedInUserService.getLoggedInUser(token).getUser().getId());
    }

    public static void updateUser(User user)
    {
        userRepository.save(user);
    }

    public static boolean isUserNameAlreadyTaken(String userName) {
        return anonUserRepository.findByAnonName(userName) != null;
    }

    public static User authenticateUser(String anonName, String hashedPassword) {
        log.info("authenticate user with email: " + anonName + " and hashedPassword: " + hashedPassword);

        AnonUser anonUser = anonUserRepository.findByAnonName(anonName);

        if (anonUser == null) {
            log.warn("No AnonUser found with username: " + anonName);
            return null;
        }
        log.info("already logged in check");
        if (LoggedInUserService.getLoggedInUser(LoggedInUserService.getTokenForAnonUser(anonUser)) != null) {
            log.info(String.format("%s was already logged in...", anonName));
            LoggedInUserService.logoutLoggedInUser(LoggedInUserService.getTokenForAnonUser(anonUser));
        }

        if (!anonUser.getHashedPassword().equals(hashedPassword)) {
            log.warn("Wrong password!");
            return null;
        }

        String doubleHashedPassword = PasswordHelper.hashPassword(anonUser.getHashedPassword(), hashedPassword);
        User user = userRepository.findByDoubleHashedPassword(doubleHashedPassword);

        if (user == null) {
            log.warn("No user found by doubleHashedPassword :" + doubleHashedPassword);
            return null;
        }

        anonUser.setUser(user);
        user.setAnonUser(anonUser);
        String token = TokenHelper.generateToken();
        LoggedInUserService.addLoggedInUser(user,anonUser,token);

        log.info("Successfully authenticated user with username: " + anonName + " and hashedPassword: " + doubleHashedPassword);
        return user;
    }

    public static boolean checkIfEmailOrUserNameIsUsed(String email, String username) {
        return userRepository.findByEmail(email) != null || anonUserRepository.findByAnonName(username) != null;
    }

    public static Set<Course> getSubscriptionsForUser(String token) {
        return courseRepository.getSubscriptionsForUser(LoggedInUserService.getLoggedInUser(token).getAnonUser().getId());
    }

    public static AnonUser getAnonUserByUserName(String userName) {
        return anonUserRepository.findByAnonName(userName);
    }

    public static boolean checkIfUserOwnsCourse(int id, String token) {
        return courseRepository.getCoursesAdminedByUser(LoggedInUserService.getLoggedInUser(token).getUser().getId()).stream()
                .anyMatch(p -> p.getId() == id);
    }

    public static Course subscribe(String code, String token) {
        Course course = courseRepository.findByInviteCode(code);
        AnonUser anonUser = LoggedInUserService.getLoggedInUser(token).getAnonUser();
        InviteCode inviteCode = inviteCodeRepository.findFirstByCodeAndCourse(code, course);

        if (inviteCode == null || course == null || anonUser == null) {
            return null;
        }

        switch (inviteCode.getMaxCopy()) {
            case 0:
                return null;
            case -1:
                break;
            default:
                inviteCode.setMaxCopy(inviteCode.getMaxCopy() - 1);
        }

        if (inviteCode.getValidUntil() != null) {
            if (inviteCode.getValidUntil().isBefore(LocalDateTime.now())) {
                return null;
            }
        }

        CourseSubs sub = new CourseSubs();
        sub.setAnonUser(anonUser);
        sub.setCourse(course);

        courseSubsRepository.save(sub);
        return course;
    }

    public static Course subscribeToFreeCourse(int id, String token) {
        Course course = courseRepository.findById(id).orElse(null);
        AnonUser anonUser = LoggedInUserService.getLoggedInUser(token).getAnonUser();

        if (course == null) {
            log.error(String.format("Couldn't find course by id: %s", id));
            return null;
        }
        if (anonUser == null) {
            log.warn(String.format("Couldn't find user by token: %s", token));
            return null;
        }
        if (course.isHidden()) {
            log.warn(String.format("%s tried to sign up to a hidden course without invite code! CourseId: %s", anonUser.getAnonName(), id));
            return null;
        }

        CourseSubs sub = new CourseSubs();
        sub.setCourse(course);
        sub.setAnonUser(anonUser);
        courseSubsRepository.save(sub);

        return course;
    }

    public static UserDTO convertToUserDTO(User user,String token) {
        return new UserDTO(token,user.getFirstName(),user.getLastName(),user.getEmail());
    }

    public static UserDTO convertToUserDTO(User user) {
        log.info("Converting user to DTO");
        return new UserDTO(LoggedInUserService.getTokenForUser(user),user.getFirstName(),user.getLastName(),user.getEmail());
    }

    public static Integer getPointsForUser(String token)
    {
        LoggedInUser loggedInUser = LoggedInUserService.getLoggedInUser(token);

        Set<Comment> comments = commentRepository.getCommentsByUserOrAnonUser(loggedInUser.getUser(),loggedInUser.getAnonUser());

        int result = 0;

        for (Comment comment:comments) {
            result += ratingRepository.getRatingsByTypeAndID(comment.getId(), Rating.RatingType.CommentRating).size();
        }

        Set<Course> courses = courseRepository.getCoursesAdminedByUser(loggedInUser.getUser().getId());

        for (Course course: courses) {
            result += ratingRepository.getRatingsByTypeAndID(course.getId(), Rating.RatingType.CourseRating).size();
        }

        return result;
    }
}
