package MAS.fitAgain.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import MAS.fitAgain.model.Masseur;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MasseurRepo extends CrudRepository<Masseur, Long> {


    @Query("select m from Masseur m join fetch m.duties where m.personId = :id")
    Optional<Masseur> findById(@Param("id") Long id);
    @Query("select m from Masseur m join fetch m.duties d left join fetch d.slots s left join fetch s.appointments " +
            "where m.personId = :id and d.date = :date")
    Optional<Masseur> findByIdAndDutyDate(@Param("id") Long id,@Param("date") LocalDate date);
    @Override
    List<Masseur> findAll();


}
