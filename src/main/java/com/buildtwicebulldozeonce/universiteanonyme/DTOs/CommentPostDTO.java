package com.buildtwicebulldozeonce.universiteanonyme.DTOs;

import lombok.Data;

@Data
public class CommentPostDTO
{
    String message;
    boolean anon;
    int refID;
}
