package com.buildtwicebulldozeonce.universiteanonyme.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InviteCodeDTO {
    int id;
    String code;
    LocalDateTime validUntil;
    int maxCopy;
}
