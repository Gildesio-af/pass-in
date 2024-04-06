package student.com.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import student.com.passin.domain.attendee.Attendee;

import java.util.List;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {
    List<Attendee> findByEventId(String eventId);
}
