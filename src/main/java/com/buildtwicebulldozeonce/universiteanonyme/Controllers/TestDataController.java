package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Course;
import com.buildtwicebulldozeonce.universiteanonyme.Models.User;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CourseService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestDataController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/createTestData")
    public void createTestData() {
        var user = User.builder()
                .name("TestUser0")
                .build();

        var course = Course.builder()
                .name("TestCourse0")
                .owner(user)
                .inviteCode("VLBIA5")
                .build();

        userService.addUser(user);
        courseService.addCourse(course);
    }
}
