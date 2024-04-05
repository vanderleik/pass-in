package com.nlwunit.passin.repositories;

import com.nlwunit.passin.domain.checkin.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckInRepository extends JpaRepository<Checkin, Integer> {

    Optional<Checkin> findByAttendeeId(String attendeeId);

}
