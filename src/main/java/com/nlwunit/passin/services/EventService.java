package com.nlwunit.passin.services;

import com.nlwunit.passin.domain.attendee.Attendee;
import com.nlwunit.passin.domain.event.Event;
import com.nlwunit.passin.domain.event.exceptions.EventNotFoundException;
import com.nlwunit.passin.dto.event.EventIdDTO;
import com.nlwunit.passin.dto.event.EventRequestDTO;
import com.nlwunit.passin.dto.event.EventResponseDTO;
import com.nlwunit.passin.repositories.AttendeeRepository;
import com.nlwunit.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;

    public EventResponseDTO getEventDetail(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + eventId));
        List<Attendee> attendeeList = attendeeRepository.findByEventId(eventId);

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

    private String createSlug(String text) {
        //Decomposição canônica
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")//seleciona os acentos e substitui por vazio
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }

}
