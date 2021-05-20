package MAS.fitAgain.repo;

import MAS.fitAgain.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import MAS.fitAgain.model.Appointment;

import java.util.Optional;

public interface AppointmentRepo extends CrudRepository<Appointment, Long> {

}
