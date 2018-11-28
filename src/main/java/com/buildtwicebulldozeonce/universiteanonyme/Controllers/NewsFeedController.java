package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Models.News;
import com.buildtwicebulldozeonce.universiteanonyme.Services.NewsFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class NewsFeedController
{
    private final NewsFeedService newsFeedService;

    @Autowired
    public NewsFeedController(NewsFeedService newsFeedService) {
        this.newsFeedService = newsFeedService;
    }

    @RequestMapping(value = "/users/{id}/newsfeed", method = RequestMethod.GET)
    public List<News> getNewFeedForUser(@PathVariable("id") int id)
    {
        return this.newsFeedService.getNewsFeedForUser(id);
    }

}
