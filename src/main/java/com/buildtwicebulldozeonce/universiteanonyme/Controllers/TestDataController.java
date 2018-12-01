package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Course;
import com.buildtwicebulldozeonce.universiteanonyme.Models.User;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CourseService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TestDataController {
    private final UserService userService;
    private final CourseService courseService;

    @Autowired
    public TestDataController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @RequestMapping(value = "/createTestData")
    public void createTestData() {
        User user = User.builder()
                .firstName("TestUser0")
                .build();

        User user2 = User.builder()
                .firstName("TestUser2")
                .build();

        Course course = Course.builder()
                .name("TestCourse0")
                .owner(user)
                .inviteCode("VLBIA5")
                .build();

        userService.addUser(user);
        userService.addUser(user2);
        courseService.addCourse(course);
    }
}
