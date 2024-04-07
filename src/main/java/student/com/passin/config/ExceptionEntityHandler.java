package student.com.passin.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import student.com.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import student.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import student.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import student.com.passin.domain.event.exceptions.EventFullException;
import student.com.passin.domain.event.exceptions.EventNotFoundException;
import student.com.passin.dto.general.ErrorResponseDTO;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventNotFound(EventNotFoundException e) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity handleEventFull(EventFullException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyRegisteredException.class)
    public ResponseEntity handleAttendeeAlreadyRegistered(AttendeeAlreadyRegisteredException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity handleCheckInAlreadyExists(CheckInAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
