package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Models.News;
import com.buildtwicebulldozeonce.universiteanonyme.Services.LoggedInUserService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.NewsFeedService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class NewsFeedController {
    @RequestMapping(value = "/user/newsfeed", method = RequestMethod.GET)
    public List<News> getNewFeedForUser(@RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        if (!LoggedInUserService.isUserLoggedIn(token)) {
            log.info("User was not logged in");
            return null;
        }

        int anonID = LoggedInUserService.getLoggedInUser(token).getAnonUser().getId();
        int id = LoggedInUserService.getLoggedInUser(token).getUser().getId();
        return NewsFeedService.getNewsFeedForUser(id, anonID);
    }

}
