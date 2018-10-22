package com.buildtwicebulldozeonce.universiteanonyme;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Course;
import com.buildtwicebulldozeonce.universiteanonyme.Models.User;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CourseService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniversiteAnonymeApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversiteAnonymeApplication.class, args);

		User user = new User();
		user.setName("TestUser0");

        Course course = new Course();
        course.setOwner(user);
        course.setName("TestCourse0");

        UserService userService = new UserService();
        userService.addUser(user);
        CourseService courseService = new CourseService();
        courseService.addCourse(course);
	}
}
