package student.com.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import student.com.passin.domain.checkin.Checkin;

public interface CheckinRepository extends JpaRepository<Checkin, String> {

}
