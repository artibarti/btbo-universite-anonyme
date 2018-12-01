package com.buildtwicebulldozeonce.universiteanonyme.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoursePulseDTO
{
    String day;
    int commentPulse;
    int questionPulse;
}
