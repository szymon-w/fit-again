package MAS.fitAgain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class TrainingType {
	
	@Id
	@GeneratedValue
	private Long trainingTypeId;
	@Column(unique=true)
	private String typeName;
	@ManyToMany(
			mappedBy="trainingTypes"
	)
	private Set<Training> trainings=new HashSet<>();

	@ManyToMany(
		mappedBy="trainingTypes"
	)
	private Set<Trainer> trainers=new HashSet<>();

	
	TrainingType() {
	}

	public TrainingType(String typeName) {
		this.typeName = typeName;
	}

	@NotBlank
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public void addTraining(Training training) {
		if(training==null) {
			throw new IllegalArgumentException("Training cannot be null");
		}
		if(!this.trainings.contains(training)) {
			this.trainings.add(training);
		try{
			training.addTrainingType(this);
		}catch(IllegalArgumentException e){	
			this.trainings.remove(training);
			throw e;
			}
		}
	}
	
	public void removeTraining(Training training) {
		if(training==null) {
			throw new IllegalArgumentException("Training cannot be null");
		}
		if(this.trainings.contains(training)) {
			this.trainings.remove(training);
		try{
			training.removeTrainingType(this);
		}catch(IllegalArgumentException e){
			this.trainings.add(training);
			throw e;
			}
		}
	}
	
	
	public Set<Trainer> getTrainers() {
		return new HashSet<Trainer>(this.trainers);
	}
		
	public void addTrainer(Trainer trainer) {
		if(trainer==null) {
			throw new IllegalArgumentException("Trainer cannot be null");
		}
		if(!this.trainers.contains(trainer)) {
			this.trainers.add(trainer);
		try{
			trainer.addTrainingType(this);
		}catch(IllegalArgumentException e){	
			this.trainers.remove(trainer);
			throw e;
			}
		}
	}
		
	public void removeTrainer(Trainer trainer) {
		if(trainer==null) {
			throw new IllegalArgumentException("Trainer cannot be null");
		}
		if(this.trainers.contains(trainer)) {
			this.trainers.remove(trainer);
		try{
			trainer.removeTrainingType(this);
		}catch(IllegalArgumentException e){
			this.trainers.add(trainer);
			throw e;
			}
		}
	}
}	
