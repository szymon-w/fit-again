package MAS.fitAgain.repo;

import MAS.fitAgain.model.Masseur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import MAS.fitAgain.model.Customer;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepo extends CrudRepository<Customer, Long> {

    @Query("select c from Customer c join fetch c.appointments where c.emailAdress = :emailAdress")
    Optional<Customer> findByEmailAdress(String emailAdress);


}
