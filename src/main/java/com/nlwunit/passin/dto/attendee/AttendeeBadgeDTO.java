package com.nlwunit.passin.dto.attendee;

public record AttendeeBadgeDTO(
        String name,
        String email,
        String checkInUrl,
        String eventId
) {
}
