package com.buildtwicebulldozeonce.universiteanonyme.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionSlimDTO {
    int id;
    String name;
    int courseId;
    boolean isActive;
}
