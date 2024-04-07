package student.com.passin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import student.com.passin.dto.attendee.AttendeeIdDTO;
import student.com.passin.dto.attendee.AttendeeRequestDTO;
import student.com.passin.dto.attendee.AttendeesListResponseDTO;
import student.com.passin.dto.event.EventIdDTO;
import student.com.passin.dto.event.EventRequestDTO;
import student.com.passin.dto.event.EventResponseDTO;
import student.com.passin.services.AttendeeService;
import student.com.passin.services.EventService;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService service;
    private final AttendeeService attendeeService;
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id){
        EventResponseDTO event = this.service.getEventDetail(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDTO eventIdDTO = service.createEvent(body);
        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();
        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
        AttendeeIdDTO attendeeIdDTO = this.service.registerAttendeeOnEvent(eventId, body);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.id()).toUri();

        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String id){
        AttendeesListResponseDTO attendeesListResponse = attendeeService.getEventsAttendee(id);
        return ResponseEntity.ok(attendeesListResponse);
    }

}
