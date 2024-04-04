package com.nlwunit.passin.domain.checkin.exceptions;

public class CheckInAllreadyExistsException extends RuntimeException {

    public CheckInAllreadyExistsException(String message) {
        super(message);
    }

}
