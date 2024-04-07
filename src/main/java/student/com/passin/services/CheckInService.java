package student.com.passin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import student.com.passin.domain.attendee.Attendee;
import student.com.passin.domain.checkin.Checkin;
import student.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import student.com.passin.repositories.CheckinRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckinRepository checkinRepository;

    public void registerCheckIn(Attendee attendee) {
        this.verifyCheckInExists(attendee.getId());
        Checkin newCheckIn = new Checkin();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatAt(LocalDateTime.now());
        this.checkinRepository.save(newCheckIn);
    }

    private void verifyCheckInExists(String attendeeId) {
        Optional<Checkin> isCheckedIn = this.getCheckIn(attendeeId);
        if(isCheckedIn.isPresent()) throw new CheckInAlreadyExistsException("Attendee already checked in");
    }

    public Optional<Checkin> getCheckIn(String attendeeId){
        return this.checkinRepository.findByAttendeeId(attendeeId);
    }
}
