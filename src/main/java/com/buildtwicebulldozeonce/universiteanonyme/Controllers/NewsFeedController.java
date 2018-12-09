package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Models.News;
import com.buildtwicebulldozeonce.universiteanonyme.Services.NewsFeedService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class NewsFeedController {
    @RequestMapping(value = "/user/newsfeed", method = RequestMethod.GET)
    public List<News> getNewFeedForUser(@RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");

        int anonID = UserService.getLoggedInUser(token).getValue2().getId();
        int id = UserService.getLoggedInUser(token).getValue1().getId();
        return NewsFeedService.getNewsFeedForUser(id, anonID);
    }

}
