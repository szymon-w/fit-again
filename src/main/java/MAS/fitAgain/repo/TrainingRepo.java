package MAS.fitAgain.repo;

import MAS.fitAgain.model.Customer;
import MAS.fitAgain.model.Masseur;
import MAS.fitAgain.model.TrainingEquipment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import MAS.fitAgain.model.Training;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingRepo extends CrudRepository<Training, Long>{

    @Query("select t from Training t join fetch t.trainingEquipments e where t.trainingId = :id and e.trainingEquipmentId = :teId")
    Optional<Training> findByIdAndTrainingEquipmentId(@Param("id") Long id, @Param("teId") Long teId);

}
