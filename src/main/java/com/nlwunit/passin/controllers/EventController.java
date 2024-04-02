package com.nlwunit.passin.controllers;

import com.nlwunit.passin.dto.attendee.AttendeeListResponseDTO;
import com.nlwunit.passin.dto.event.EventIdDTO;
import com.nlwunit.passin.dto.event.EventRequestDTO;
import com.nlwunit.passin.dto.event.EventResponseDTO;
import com.nlwunit.passin.services.AttendeeService;
import com.nlwunit.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String eventId) {
        return ResponseEntity.ok(eventService.getEventDetail(eventId));
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDTO eventIdDTO = eventService.createEvent(body);
        URI uri = uriComponentsBuilder.path("/events/{eventId}").buildAndExpand(eventIdDTO.eventId()).toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @GetMapping("/attendees/{eventId}")
    public ResponseEntity<AttendeeListResponseDTO> getEventAttendees(@PathVariable String eventId) {
        return ResponseEntity.ok(attendeeService.getEventsAttendee(eventId));
    }

}
