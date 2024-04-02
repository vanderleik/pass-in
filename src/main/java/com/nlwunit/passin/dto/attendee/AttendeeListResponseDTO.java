package com.nlwunit.passin.dto.attendee;

import lombok.Getter;

import java.util.List;

@Getter
public record AttendeeListResponseDTO (List<AttendeeDetails> attendees){

}
