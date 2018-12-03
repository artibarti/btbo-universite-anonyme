package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseSlimDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.UserDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.PasswordHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.TokenHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
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
        HashMap<String, String> values
                = Functions.getValuesFromHttpHeader(headers, "username", "password");

        User user = userService.authenticateUser(values.get("username"), values.get("password"));

        if (user != null)
            return user.convertToDTO();
        else
            return new UserDTO();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public UserDTO register(@RequestHeader HttpHeaders headers)
    {
        HashMap<String, String> values = Functions.getValuesFromHttpHeader(
                headers, "username", "password", "email", "firstname", "lastname");

        if (userService.checkIfEmailOrUserNameIsUsed(values.get("email"), values.get("username")) || values.get("password") == "")
        {
            log.info("registration failed");
            UserDTO result = new UserDTO();
            return result;
        }
        else
        {
            log.info("registration success");
            AnonUser anonUser = AnonUser.builder()
                    .anonName(values.get("username"))
                    .hashedPassword(values.get("password"))
                    .build();
            User user = User.builder()
                    .firstName(values.get("firstname"))
                    .lastName(values.get("lastname"))
                    .doubleHashedPassword(PasswordHelper.hashPassword(anonUser.getHashedPassword(), values.get("password")))
                    .email(values.get("email"))
                    .build();

            userService.addAnonUser(anonUser);
            userService.addUser(user);
            return user.convertToDTO();
        }
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public void addUser(@RequestBody UserDTO user, @RequestHeader HttpHeaders headers)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUser(@RequestHeader HttpHeaders headers, @PathVariable("id") int id)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        return userService.getUser(id);
    }

    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@RequestHeader HttpHeaders headers, @PathVariable("id") int id)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        userService.deleteUser(id);
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.PUT)
    public void updateUser(@RequestBody UserDTO user, @RequestHeader HttpHeaders headers)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        log.info("/user/update endpoint reached");
    }

    @RequestMapping(value = "/user/adminroles", method = RequestMethod.GET)
    public Set<CourseSlimDTO> getAdminRolesForUser(@RequestHeader HttpHeaders headers)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        log.info("/user/adminroles endpoint reached");

        return userService.getCoursesAdminedByUser(token).stream()
                .map(Course::convertToSlimDTO)
                .collect(Collectors.toSet());
    }

    @RequestMapping(value = "/user/subs", method = RequestMethod.GET)
    public Set<CourseSlimDTO> getSubscriptions(@RequestHeader HttpHeaders headers)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");

        return userService.getSubscriptionsForUser(token).stream()
                .map(Course::convertToSlimDTO)
                .collect(Collectors.toSet());
    }

}
