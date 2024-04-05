package com.nlwunit.passin.domain.checkin;

import com.nlwunit.passin.domain.attendee.Attendee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "check_ins")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Checkin {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "attendee_id", nullable = false)
    private Attendee attendee;

}
