package com.buildtwicebulldozeonce.universiteanonyme.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseSlimDTO
{
    int id;
    String name;
    boolean hidden;
}
