package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseSlimDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.UserDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.PasswordHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Models.AnonUser;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Course;
import com.buildtwicebulldozeonce.universiteanonyme.Models.User;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class UserController {

    @RequestMapping(value = "/amILoggedIn", method = RequestMethod.GET)
    public boolean amILoggedIn(@RequestHeader HttpHeaders headers) {
        log.debug(""+headers);
        String token = Functions.getValueFromHttpHeader(headers,"token");
        if (UserService.getLoggedInUser(token) == null) {
            log.info("User not Logged in");
            return false;
        }
        log.info("User already logged in with token: "+token);
        return true;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public UserDTO login(@RequestHeader HttpHeaders headers) {
        HashMap<String, String> values
                = Functions.getValuesFromHttpHeader(headers, "username", "password");

        User user = UserService.authenticateUser(values.get("username"), values.get("password"));

        if (user != null)
            return user.convertToDTO();
        else
            return new UserDTO();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public UserDTO register(@RequestHeader HttpHeaders headers) {
        HashMap<String, String> values = Functions.getValuesFromHttpHeader(
                headers, "username", "password", "email", "firstname", "lastname");

        if (UserService.checkIfEmailOrUserNameIsUsed(values.get("email"), values.get("username")) || values.get("password") == null) {
            log.info("registration failed");
            UserDTO result = new UserDTO();
            return result;
        } else {
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

            UserService.addAnonUser(anonUser);
            UserService.addUser(user);
            return user.convertToDTO();
        }
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUser(@RequestHeader HttpHeaders headers, @PathVariable("id") int id)
    {
        return null;
    }

    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@RequestHeader HttpHeaders headers, @PathVariable("id") int id)
    {

    }

    @RequestMapping(value = "/user/update", method = RequestMethod.PUT)
    public UserDTO updateUser(@RequestBody UserDTO user, @RequestHeader HttpHeaders headers)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        User updated = UserService.getLoggedInUser(token).getValue1();

        if (updated != null)
        {
            updated.setEmail(user.getEmail());
            updated.setFirstName(user.getFirstName());
            updated.setLastName(user.getLastName());

            UserService.updateUser(updated);
            return updated.convertToDTO();
        }

        return null;
    }

    @RequestMapping(value = "/user/adminroles", method = RequestMethod.GET)
    public Set<CourseSlimDTO> getAdminRolesForUser(@RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");

        return UserService.getCoursesAdminedByUser(token).stream()
                .map(Course::convertToSlimDTO)
                .collect(Collectors.toSet());
    }

    @RequestMapping(value = "/user/subs", method = RequestMethod.GET)
    public Set<CourseSlimDTO> getSubscriptions(@RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");

        return UserService.getSubscriptionsForUser(token).stream()
                .map(Course::convertToSlimDTO)
                .collect(Collectors.toSet());
    }

    @RequestMapping(value = "/courses/subscribe", method = RequestMethod.GET)
    public CourseSlimDTO subscribe(@RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        String inviteCode = Functions.getValueFromHttpHeader(headers, "code");

        Course course = UserService.subscribe(inviteCode, token);
        if (course == null)
            return null;
        else
            return course.convertToSlimDTO();
    }

    @RequestMapping(value = "/courses/{id}/subscribe", method = RequestMethod.GET)
    public CourseSlimDTO subscribeToFreeCourse(@PathVariable("id") int id, @RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        return UserService.subscribeToFreeCourse(id, token).convertToSlimDTO();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public void logout2(@RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");

        if (UserService.logoutLoggedInUserByToken(token))
        {
            log.trace("Successful logout!");
            return;
        }

        log.error("Error while logging out...");
    }

    @RequestMapping(value = "/user/points", method = RequestMethod.GET)
    public Integer getPointsForUser(@RequestHeader HttpHeaders headers)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        return UserService.getPointsForUser(token);
    }

}
