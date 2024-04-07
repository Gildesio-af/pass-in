package student.com.passin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import student.com.passin.domain.attendee.Attendee;
import student.com.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import student.com.passin.domain.checkin.Checkin;
import student.com.passin.dto.attendee.AttendeeBadgeResponseDTO;
import student.com.passin.dto.attendee.AttendeeDetails;
import student.com.passin.dto.attendee.AttendeesListResponseDTO;
import student.com.passin.dto.attendee.AttendeeBadgeDTO;
import student.com.passin.repositories.AttendeeRepository;
import student.com.passin.repositories.CheckinRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId) {
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<Checkin> checkIn = this.checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(Checkin::getCreatAt).orElse(null);
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(),checkedInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public void verifyAttendeeSubscription(String email, String eventId) {
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
        if(isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyRegisteredException("Attendee is already registered");
    }

    public Attendee registerAttendee(Attendee newAttendee) {
       return this.attendeeRepository.save(newAttendee);
    }

    public void checkInAttendee(String attendeeId) {
        Attendee attendee = getAttendee(attendeeId);

        this.checkInService.registerCheckIn(attendee);
    }

    private Attendee getAttendee(String attendeeId) {
        return this.attendeeRepository.findById(attendeeId).orElseThrow(() -> new RuntimeException("Attendee not found with id: " + attendeeId));
    }



    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = this.getAttendee(attendeeId);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();
        return new AttendeeBadgeResponseDTO(new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri, attendee.getEvent().getId()));
    }
}
