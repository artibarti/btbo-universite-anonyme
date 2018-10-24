package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.RatingRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<Rating> getAllRating() {
        return Lists.newArrayList(ratingRepository.findAll());
    }

    public Rating getRating(int id) {
        return ratingRepository.findById(id).orElse(null);
    }

    public void addRating(@NonNull Rating rating) {
        ratingRepository.save(rating);
    }
}
