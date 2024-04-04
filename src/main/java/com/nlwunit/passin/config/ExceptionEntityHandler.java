package com.nlwunit.passin.config;

import com.nlwunit.passin.domain.attendee.exceptions.AttendeeAlreadyExistsException;
import com.nlwunit.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import com.nlwunit.passin.domain.checkin.exceptions.CheckInAllreadyExistsException;
import com.nlwunit.passin.domain.event.exceptions.EventFullException;
import com.nlwunit.passin.domain.event.exceptions.EventNotFoundException;
import com.nlwunit.passin.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventFull(EventFullException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyExistsException.class)
    public ResponseEntity handleAttendeeAllreadyExists(AttendeeAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAllreadyExistsException.class)
    public ResponseEntity handleCheckInAllreadyExists(CheckInAllreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
