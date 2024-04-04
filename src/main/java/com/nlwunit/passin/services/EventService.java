package com.nlwunit.passin.services;

import com.nlwunit.passin.domain.attendee.Attendee;
import com.nlwunit.passin.domain.event.Event;
import com.nlwunit.passin.domain.event.exceptions.EventFullException;
import com.nlwunit.passin.domain.event.exceptions.EventNotFoundException;
import com.nlwunit.passin.dto.attendee.AttendeeIdDTO;
import com.nlwunit.passin.dto.attendee.AttendeeRequestDTO;
import com.nlwunit.passin.dto.event.EventIdDTO;
import com.nlwunit.passin.dto.event.EventRequestDTO;
import com.nlwunit.passin.dto.event.EventResponseDTO;
import com.nlwunit.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId) {
        Event event = getEventById(eventId);
        List<Attendee> attendeeList = attendeeService.getAllAttendeesFromEvent(eventId);

        return new EventResponseDTO(event, attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventDTO) {
        Event newEvent = new Event();
        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
        newEvent.setSlug(createSlug(eventDTO.title()));

        eventRepository.save(newEvent);
        return new EventIdDTO(newEvent.getId());
    }

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeDTO) {
        attendeeService.verifyAttendeeSubscription(attendeeDTO.email(), eventId);

        Event event = getEventById(eventId);
        List<Attendee> attendeeList = attendeeService.getAllAttendeesFromEvent(eventId);

        if (event.getMaximumAttendees() <= attendeeList.size()) {
            throw new EventFullException("Event is full");
        }

        Attendee newAttendee = getNewAttendee(attendeeDTO, event);
        attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }

    private static Attendee getNewAttendee(AttendeeRequestDTO attendeeDTO, Event event) {
        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendeeDTO.name());
        newAttendee.setEmail(attendeeDTO.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());
        return newAttendee;
    }

    private Event getEventById(String eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + eventId));
    }

    private String createSlug(String text) {
        //Decomposição canônica
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")//seleciona os acentos e substitui por vazio
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }

}
