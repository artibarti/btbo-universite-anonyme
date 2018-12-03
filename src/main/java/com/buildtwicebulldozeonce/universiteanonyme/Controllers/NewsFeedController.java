package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Models.News;
import com.buildtwicebulldozeonce.universiteanonyme.Services.NewsFeedService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/users/{id}/newsfeed", method = RequestMethod.GET)
    public List<News> getNewFeedForUser(@PathVariable("id") int id)
    {
        return this.newsFeedService.getNewsFeedForUser(id,
                this.userService.getLoggedInUserByUserId(id).getValue2().getId());
    }

}
