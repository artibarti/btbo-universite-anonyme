package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Helpers.PasswordHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.TokenHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.*;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Log
@Service
public class UserService {

    private static UserRepository userRepository;
    private static AnonUserRepository anonUserRepository;
    private static CourseRepository courseRepository;
    private static RatingRepository ratingRepository;
    private static InviteCodeRepository inviteCodeRepository;
    private static CourseSubsRepository courseSubsRepository;

    private static List<Triplet<String, User, AnonUser>> loggedInUsers = new ArrayList<>();

    @Autowired
    public UserService(UserRepository userRepository, AnonUserRepository anonUserRepository,
                       CourseRepository courseRepository, RatingRepository ratingRepository,
                       InviteCodeRepository inviteCodeRepository,
                       CourseSubsRepository courseSubsRepository) {

        UserService.userRepository = userRepository;
        UserService.anonUserRepository = anonUserRepository;
        UserService.courseRepository = courseRepository;
        UserService.ratingRepository = ratingRepository;
        UserService.inviteCodeRepository = inviteCodeRepository;
        UserService.courseSubsRepository = courseSubsRepository;
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
        return courseRepository.getCoursesAdminedByUser(getLoggedInUser(token).getValue1().getId());
    }

    public static void updateUser(User user) {
        if (userRepository.existsById(user.getId()))
            userRepository.save(user);
    }

    public static boolean isUserNameAlreadyTaken(String userName) {
        return anonUserRepository.findByAnonName(userName) != null;
    }

    public static User authenticateUser(String anonName, String hashedPassword) {
        log.info("authenticate user with email: " + anonName + " and hashedPassword: " + hashedPassword);

        AnonUser anonUser = anonUserRepository.findByAnonName(anonName);


        if (anonUser == null || !anonUser.getHashedPassword().equals(hashedPassword)) {
            log.warning("No AnonUser found with username: " + anonName);
            return null;
        }

        String doubleHashedPassword = PasswordHelper.hashPassword(anonUser.getHashedPassword(), hashedPassword);
        User user = userRepository.findByDoubleHashedPassword(doubleHashedPassword);

        if (user == null) {
            log.warning("No user found by doubleHashedPassword :" + doubleHashedPassword);
            return null;
        }

        anonUser.setUser(user);
        user.setAnonUser(anonUser);
        String token = TokenHelper.generateToken();
        user.setToken(token);
        loggedInUsers.add(new Triplet<>(token, user, anonUser));

        log.info("Successfully authenticated user with username: " + anonName + " and hashedPassword: " + doubleHashedPassword);
        return user;
    }

    public static boolean checkIfEmailOrUserNameIsUsed(String email, String username) {
        return userRepository.findByEmail(email) != null || anonUserRepository.findByAnonName(username) != null;
    }

    public static Set<Course> getSubscriptionsForUser(String token) {
        return courseRepository.getSubscriptionsForUser(getLoggedInUser(token).getValue2().getId());
    }

    public static AnonUser getAnonUserByUserName(String userName) {
        return anonUserRepository.findByAnonName(userName);
    }

    public static Triplet<String, User, AnonUser> getLoggedInUser(String token) {
        return loggedInUsers
                .stream()
                .filter(triplet -> triplet.getValue0().equals(token))
                .findFirst()
                .orElse(null);
    }

    public static Triplet<String, User, AnonUser> getLoggedInUserByUserId(int userId) {
        return loggedInUsers
                .stream()
                .filter(triplet -> triplet.getValue1().getId() == userId)
                .findFirst()
                .orElse(null);
    }

    public static Triplet<String, User, AnonUser> getLoggedInUserByAnonUserId(int anonUserId) {
        return loggedInUsers
                .stream()
                .filter(triplet -> triplet.getValue2().getId() == anonUserId)
                .findFirst()
                .orElse(null);
    }

    public static boolean checkIfUserOwnsCourse(int id, String token) {
        return courseRepository.getCoursesAdminedByUser(getLoggedInUser(token).getValue1().getId()).stream()
                .anyMatch(p -> p.getId() == id);
    }

    public static Course subscribe(String code, String token) {
        Course course = courseRepository.findByInviteCode(code);
        AnonUser anonUser = getLoggedInUser(token).getValue2();
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

    public Course subscribeToFreeCourse(int id, String token)
    {
        Course course = courseRepository.findById(id).get();
        AnonUser anonUser = getLoggedInUser(token).getValue2();

        if (course == null || course.isHidden() || anonUser == null)
        {
            log.info("something is wrong");
            return null;
        }

        else
        {
            CourseSubs sub = new CourseSubs();
            sub.setCourse(course);
            sub.setAnonUser(anonUser);
            courseSubsRepository.save(sub);

            return course;
        }
    }
}
