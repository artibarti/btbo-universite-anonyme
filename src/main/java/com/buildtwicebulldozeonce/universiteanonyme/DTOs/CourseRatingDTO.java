package com.buildtwicebulldozeonce.universiteanonyme.DTOs;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseRatingDTO
{
    int numberOfRatings;
    double sum;
}
