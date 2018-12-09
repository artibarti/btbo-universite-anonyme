package com.buildtwicebulldozeonce.universiteanonyme.Models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class News {
    private String content;
    private String anonUserName;
    private LocalDateTime timestamp;
    private String type;
    private int refID;
    private String refName;

}
