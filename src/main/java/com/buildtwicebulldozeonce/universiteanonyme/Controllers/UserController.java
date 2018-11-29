package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseSlimDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.UserDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.PasswordHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Log
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public UserDTO login(@RequestHeader HttpHeaders headers)
    {
        String userName = headers.getFirst("username");
        String password = headers.getFirst("password");
        log.info(String.format("username %s, password: %s",userName,password));
        User user = userService.authenticateUser(userName, password);
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
        String firstName = headers.getFirst("firstname");
        String lastName = headers.getFirst("lastname");
        String userName = headers.getFirst("username");

        if (userService.checkIfEmailIsUsed(email) || userService.isUserNameAlreadyTaken(userName) || password == null)
        {
            UserDTO result = new UserDTO();
            return result;
        }
        else
        {
            AnonUser anonUser = AnonUser.builder()
                    .anonName(userName)
                    .hashedPassword(PasswordHelper.hashPassword(password,""))
                    .build();
            User user = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .doubleHashedPassword(PasswordHelper.hashPassword(anonUser.getHashedPassword(),password))
                    .email(email)
                    .build();

            userService.addAnonUser(anonUser);
            userService.addUser(user);
            return user.convertToDTO();
        }
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

    @RequestMapping(value = "/users/{id}/delete", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id)
    {
        userService.deleteUser(id);
    }

    @RequestMapping(value = "/users/{id}/update", method = RequestMethod.PUT)
    public void updateUser(@RequestBody User user)
    {
        userService.updateUser(user);
    }

    @RequestMapping(value = "/users/{id}/adminroles", method = RequestMethod.GET)
    public Set<CourseSlimDTO> getAdminRolesForUser(@PathVariable int id)
    {
        return userService.getCoursesAdminedByUser(id).stream()
                .map(Course::convertToSlimDTO)
                .collect(Collectors.toSet());
    }

}
