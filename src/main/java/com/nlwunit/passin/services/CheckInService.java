package com.nlwunit.passin.services;

import com.nlwunit.passin.domain.attendee.Attendee;
import com.nlwunit.passin.domain.checkin.Checkin;
import com.nlwunit.passin.domain.checkin.exceptions.CheckInAllreadyExistsException;
import com.nlwunit.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee) {
        verifyCheckinExists(attendee.getId());
        Checkin newCheckin = new Checkin();
        newCheckin.setAttendee(attendee);
        newCheckin.setCreatedAt(LocalDateTime.now());

        checkInRepository.save(newCheckin);
    }

    private void verifyCheckinExists(String id) {
        Optional<Checkin> isCheckedIn = checkInRepository.findByAttendeeId(id);
        if (isCheckedIn.isPresent()) {
            throw new CheckInAllreadyExistsException("Attendee already checked in");
        }
    }


}
