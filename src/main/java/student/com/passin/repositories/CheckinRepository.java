package student.com.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import student.com.passin.domain.checkin.Checkin;

import java.util.Optional;

public interface CheckinRepository extends JpaRepository<Checkin, String> {
    Optional<Checkin> findByAttendeeId(String attendeeId);
}
