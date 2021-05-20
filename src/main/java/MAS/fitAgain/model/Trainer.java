package MAS.fitAgain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.validation.constraints.Size;
import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="personId")
public class Trainer extends Employee {
	
	@OneToMany(mappedBy="trainer")
	private Set<Training> trainings=new HashSet<>();
	
	@ManyToMany()
	@JoinTable(
		name="TrainerTrainingType",
		joinColumns=@JoinColumn(name="trainerId"),
		inverseJoinColumns=@JoinColumn(name="trainingTypeId")
	)
	@Size(min=1)
	private Set<TrainingType> trainingTypes=new HashSet<>();
	
	public Trainer(int idNumber, String firstName, String lastName, LocalDate birthDate, boolean b2bPartner, BigDecimal hourlyRate,
			BigDecimal salaryBasis) {
		super(idNumber, firstName,lastName, birthDate, b2bPartner,hourlyRate,salaryBasis);
	}

	public Set<Training> getTrainings() {
		return new HashSet<Training>(this.trainings);
	}
		
	public void addTraining(Training training) {
		if(training==null) {
			throw new IllegalArgumentException("Training cannot be null");
		}
		if(!this.trainings.contains(training)) {
			this.trainings.add(training);
			training.setTrainer(this);
		}
	}
		
	public void removeTraining(Training training) {
		if(training==null) {
			throw new IllegalArgumentException("Training cannot be null");
		}
		if(this.trainings.contains(training)) {
			this.trainings.remove(training);
			training.setTrainer(null);
		}
	}
	
	
	public Set<TrainingType> getTrainingTypes() {
		return new HashSet<TrainingType>(this.trainingTypes);
	}
		
	public void addTrainingType(TrainingType trainingType) {
		if(trainingType==null) {
			throw new IllegalArgumentException("TrainingType cannot be null");
		}
		if(!this.trainingTypes.contains(trainingType)) {
			this.trainingTypes.add(trainingType);
			trainingType.addTrainer(this);
		}
	}
		
	public void removeTrainingType(TrainingType trainingType) {
		if(trainingType==null) {
			throw new IllegalArgumentException("TrainingType cannot be null");
		}
		if(this.trainingTypes.contains(trainingType)) {
			this.trainingTypes.remove(trainingType);
			trainingType.removeTrainer(this);
		}
	}

	
	
	
}
