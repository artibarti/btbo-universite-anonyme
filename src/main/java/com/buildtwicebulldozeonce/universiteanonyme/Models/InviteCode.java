package com.buildtwicebulldozeonce.universiteanonyme.Models;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.InviteCodeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class InviteCode
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String code;

    @Column
    private LocalDateTime validUntil;

    @Column
    private int maxCopy;

    @ManyToOne
    private Course course;

    public InviteCodeDTO convertToDTO()
    {
        return new InviteCodeDTO(this.id, this.code, this.validUntil, this.maxCopy);
    }
}
