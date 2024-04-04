package com.nlwunit.passin.controllers;

import com.nlwunit.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.nlwunit.passin.services.AttendeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeService attendeeService;

    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(
            @PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        return ResponseEntity.ok(attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder));
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        attendeeService.checkInAttendee(attendeeId);
        URI uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();
        return ResponseEntity.created(uri).build();
    }

}
