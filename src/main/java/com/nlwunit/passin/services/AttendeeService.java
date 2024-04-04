package com.nlwunit.passin.services;

import com.nlwunit.passin.domain.attendee.Attendee;
import com.nlwunit.passin.domain.attendee.exception.AttendeeAlreadyExistsException;
import com.nlwunit.passin.domain.checkin.Checkin;
import com.nlwunit.passin.dto.attendee.AttendeeDetails;
import com.nlwunit.passin.dto.attendee.AttendeeListResponseDTO;
import com.nlwunit.passin.repositories.AttendeeRepository;
import com.nlwunit.passin.repositories.CheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckinRepository checkinRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return attendeeRepository.findByEventId(eventId);
    }

    public AttendeeListResponseDTO getEventsAttendee(String eventId) {
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<Checkin> checkin = checkinRepository.findByAttendeeId(attendee.getId());
            LocalDateTime checkedInAt = checkin.<LocalDateTime>map(Checkin::getCreatedAt).orElse(null);
            return new AttendeeDetails(
                    attendee.getId(),
                    attendee.getName(),
                    attendee.getEmail(),
                    attendee.getCreatedAt(),
                    checkedInAt);
        }).toList();

        return new AttendeeListResponseDTO(attendeeDetailsList);
    }

    public Attendee registerAttendee(Attendee newAttendee) {
        return attendeeRepository.save(newAttendee);

    }

    public void verifyAttendeeSubscription(String email, String eventId) {
        Optional<Attendee> isAttendeeRegistered = attendeeRepository.findByEmailAndEventId(email, eventId);
        if (isAttendeeRegistered.isPresent()) {
            throw new AttendeeAlreadyExistsException("Attendee already registered on this event");
        }

    }
}
