package student.com.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import student.com.passin.domain.event.Event;

public interface EventRepository extends JpaRepository<Event, String> {

}
