package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.RatingRepository;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class RatingService {
    private  static RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        RatingService.ratingRepository = ratingRepository;
    }

    public static void addRating(@NonNull Rating rating)
    {
        ratingRepository.save(rating);
    }

    public static void deleteRatingByRefIdAndType(int id, Rating.RatingType type) {
        ratingRepository.getRatingByRefIDAndType(id, type).forEach(ratingRepository::delete);
    }
}
