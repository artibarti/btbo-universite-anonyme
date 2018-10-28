package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public void addUser(@RequestBody User user)
    {
        userService.addUser(user);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int id)
    {
        return userService.getUser(id);
    }

    @RequestMapping(value = "/users/{id}/adminroles", method = RequestMethod.GET)
    public Set<Admin> getAdminRolesForUser(@PathVariable int id)
    {
        return userService.getAdminRolesForUser(id);
    }

    @RequestMapping(value = "/users/{id}/comments", method = RequestMethod.GET)
    public Set<Comment> getAllCommentsFromUser(@PathVariable int id)
    {
        return userService.getAllCommentsFromUser(id);
    }

    @RequestMapping(value = "/users/{id}/questions", method = RequestMethod.GET)
    public Set<Question> getAllQuestionsFromUser(@PathVariable int id)
    {
        return userService.getAllQuestionsFromUser(id);
    }

    @RequestMapping(value = "/users/{id}/courses", method = RequestMethod.GET)
    public Set<Course> getCoursesCreatedByUser(@PathVariable int id)
    {
        return userService.getCoursesCreatedByUser(id);
    }

    @RequestMapping(value = "/users/{id}/ratings", method = RequestMethod.GET)
    public Set<Rating> getAllRatingsFromUser(@PathVariable int id)
    {
        return userService.getAllRatingsFromUser(id);
    }
}
