package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Helpers.PasswordHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.TokenHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.*;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Log
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AnonUserRepository anonUserRepository;
    private final CourseRepository courseRepository;
    private  final RatingRepository ratingRepository;
    private List<Triplet<String, User, AnonUser>> loggedInUsers = new ArrayList<>();

    @Autowired
    public UserService(UserRepository userRepository, AnonUserRepository anonUserRepository,
                       CourseRepository courseRepository, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.anonUserRepository = anonUserRepository;
        this.courseRepository = courseRepository;
        this.ratingRepository = ratingRepository;
    }

    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public void addUser(@NonNull User user) {
        userRepository.save(user);
    }

    public void addAnonUser(@NonNull AnonUser anonUser) {
        anonUserRepository.save(anonUser);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public Set<Course> getCoursesAdminedByUser(int id) {
        return courseRepository.getCoursesAdminedByUser(id);
    }

    public void updateUser(User user)
    {
        if (userRepository.existsById(user.getId()))
            userRepository.save(user);
    }

    public boolean isUserNameAlreadyTaken(String userName)
    {
        return anonUserRepository.findByAnonName(userName) != null;
    }

    public User authenticateUser(String anonName, String hashedPassword)
    {
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

    public boolean checkIfEmailIsUsed(String email)
    {
        return userRepository.findByEmail(email) != null;
    }

    public Set<Course> getSubscribtionsForUser(int id)
    {
        return this.courseRepository.getSubscribtionsForUser(id);
    }

    public AnonUser getAnonUserByUserName(String userName)
    {
        return anonUserRepository.findByAnonName(userName);
    }

    public Triplet<String, User, AnonUser> getLoggedInUser(String token)
    {
        return loggedInUsers
                .stream()
                .filter(triplet -> triplet.getValue0().equals(token))
                .findFirst()
                .orElse(null);
    }

    public Triplet<String, User, AnonUser> getLoggedInUserByUserId(int userId)
    {
        return loggedInUsers
                .stream()
                .filter(triplet -> triplet.getValue1().getId() == userId)
                .findFirst()
                .orElse(null);
    }

    public Triplet<String, User, AnonUser> getLoggedInUserByAnonUserId(int anonUserId)
    {
        return loggedInUsers
                .stream()
                .filter(triplet -> triplet.getValue2().getId() == anonUserId)
                .findFirst()
                .orElse(null);
    }
}
