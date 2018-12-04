package com.buildtwicebulldozeonce.universiteanonyme.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseFatDTO
{
    int id;
    String name;
    String description;
    boolean hidden;
}
