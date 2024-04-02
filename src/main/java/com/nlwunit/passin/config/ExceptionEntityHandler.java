package com.nlwunit.passin.config;

import com.nlwunit.passin.domain.event.exceptions.EventNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleNotFoundException(EventNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

}
