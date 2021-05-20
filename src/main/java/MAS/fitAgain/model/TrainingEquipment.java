package MAS.fitAgain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class TrainingEquipment {

	@Id
	@GeneratedValue
	private Long trainingEquipmentId;
	private String name;
	@ManyToMany(
		mappedBy="trainingEquipments"
	)
	private Set<Training> trainings = new HashSet<Training>();
	
	public TrainingEquipment() {
	}
	
	public TrainingEquipment(String name) {
		setName(name);
	}

	@NotBlank(message="Name cannot be blank")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
			this.name = name;
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
		try{
			training.addTrainingEquipment(this);
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
			training.removeTrainingEquipment(this);
		}catch(IllegalArgumentException e){
			this.trainings.add(training);
			throw e;
			}
		}
	}
}

