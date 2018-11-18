package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.UserDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public UserDTO login(@RequestHeader HttpHeaders headers)
    {
        String email = headers.getFirst("email");
        String password = headers.getFirst("password");

        User user = userService.authenticateUser(email, password);
        if (user != null)
        {
            return user.convertToDTO();
        }
        else
        {
            UserDTO empty = new UserDTO();
            return empty;
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public UserDTO register(@RequestHeader HttpHeaders headers)
    {
        String email = headers.getFirst("email");
        String password = headers.getFirst("password");
        String name = headers.getFirst("name");

        if (userService.checkIfUserExists(email))
        {
            UserDTO result = new UserDTO();
            return result;
        }
        else
        {
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setDoubleHashedPassword(password);

            userService.addUser(user);
            return user.convertToDTO();
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public void addUser(@RequestBody User user)
    {
        userService.addUser(user);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int id)
    {
        return userService.getUser(id);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id)
    {
        userService.deleteUser(id);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public void updateUser(@RequestBody User user)
    {
        userService.updateUser(user);
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
