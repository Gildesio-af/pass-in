package student.com.passin.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.com.passin.domain.attendee.Attendee;
import student.com.passin.domain.event.Event;
import student.com.passin.dto.event.EventRequestDTO;
import student.com.passin.dto.event.EventResponseDTO;
import student.com.passin.repositories.AttendeeRepository;
import student.com.passin.repositories.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;

    public EventResponseDTO getEventDetail(String eventId){
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));
        List<Attendee> attendees = this.attendeeRepository.findByEventId(eventId);
        return new EventResponseDTO(event, attendees.size());
    }

    public void createEvent(EventRequestDTO eventDTO) {
        Event newEvent = new Event();
        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());

    }

    private String cretateSlug(String text) {
        return "";
    }
}
