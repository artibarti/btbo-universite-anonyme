package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Models.News;
import com.buildtwicebulldozeonce.universiteanonyme.Services.NewsFeedService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
public class NewsFeedController
{
    private final NewsFeedService newsFeedService;
    private final UserService userService;

    @Autowired
    public NewsFeedController(NewsFeedService newsFeedService, UserService userService)
    {
        this.newsFeedService = newsFeedService;
        this.userService = userService;
    }

    @RequestMapping(value = "/user/newsfeed", method = RequestMethod.GET)
    public List<News> getNewFeedForUser(@RequestHeader HttpHeaders headers)
    {
        String token = Functions.getValueFromHttpHeader(headers,"token");

        int anonID = this.userService.getLoggedInUser(token).getValue2().getId();
        int id = this.userService.getLoggedInUser(token).getValue1().getId();
        return this.newsFeedService.getNewsFeedForUser(id, anonID);
    }

}
