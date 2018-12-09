package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Models.AnonUser;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Models.User;
import com.buildtwicebulldozeonce.universiteanonyme.Services.RatingService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Triplet;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class RatingController {

    @RequestMapping(value = "/token/{token}/isanon/{isAnon}/ratingtype/{ratingType}/ratingvalue/{ratingValue}/refID/{refId}", method = RequestMethod.GET)
    public String addRating(@PathVariable("token") String token, @PathVariable("isAnon") boolean isAnon, @PathVariable("ratingType") String ratingType, @PathVariable("ratingValue") int ratingValue, @PathVariable("refId") int refId) {
        Triplet<String, User, AnonUser> loggedInUser = UserService.getLoggedInUser(token);
        if (loggedInUser == null) {
            log.error("Couldn't find user by token:" + token);
            return "Couldn't find user by token:" + token;
        }

        Rating.RatingType ratingTypeEnum = Rating.RatingType.valueOf(ratingType);
        Rating rating;

        if (isAnon) {
            rating = Rating.builder()
                    .anonUser(loggedInUser.getValue2())
                    .type(ratingTypeEnum)
                    .refID(refId)
                    .value(ratingValue)
                    .build();
        } else {
            rating = Rating.builder()
                    .user(loggedInUser.getValue1())
                    .type(ratingTypeEnum)
                    .refID(refId)
                    .value(ratingValue)
                    .build();
        }

        RatingService.addRating(rating);

        return "Rating saved";
    }
}
