package MAS.fitAgain.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Room {

	@Id
	@GeneratedValue
	private Long roomId;
	@Column(unique = true)
	private int number;
	private int basicMassageStandsNumber;
	private int additionalMassageStandsNumber;
	private boolean trainingPlace;
	
	@OneToMany(mappedBy="room")
	private Set<Duty> duties=new HashSet<Duty>();
	
	@OneToMany(mappedBy="room")
	private Set<Training> trainings=new HashSet<>();

	
	public Room() {
	}
	
	public Room(int number, int basicMassageStandsNumber, int additionalMassageStandsNumber, boolean trainingPlace) {
		setNumber(number);
		setAdditionalMassageStandsNumber(additionalMassageStandsNumber);
		setBasicMassageStandsNumber(basicMassageStandsNumber);
		setTrainingPlace(trainingPlace);
	}


	@Min(0)
	public int getAdditionalMassageStandsNumber() {
		return additionalMassageStandsNumber;
	}

	public void setAdditionalMassageStandsNumber(int additionalMassageStandsNumber) {
		this.additionalMassageStandsNumber = additionalMassageStandsNumber;
	}

	@Min(0)
	public int getBasicMassageStandsNumber() {
		return basicMassageStandsNumber;
	}
	
	public void setBasicMassageStandsNumber(int basicMassageStandsNumber) {
		this.basicMassageStandsNumber = basicMassageStandsNumber;
	}

	@Min(1)
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public boolean isTrainingPlace() {
		return trainingPlace;
	}
	
	public void setTrainingPlace(boolean trainingPlace) {
		this.trainingPlace = trainingPlace;
	}
	
	public Set<Duty> getDuties() {
		return new HashSet<Duty>(duties);
	}
	
	public void addDuty(Duty duty) {
		if(duty==null) {
			throw new IllegalArgumentException("Duty cannot be null");
		}
		if(!this.duties.contains(duty)) {
			this.duties.add(duty);
			duty.setRoom(this);
		}
	}
	
	public void removeDuty(Duty duty) {
		if(duty==null) {
			throw new IllegalArgumentException("Duty cannot be null");
		}
		if(this.duties.contains(duty)) {
			this.duties.remove(duty);
			duty.setRoom(null);
		}
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
			training.setRoom(this);
		}
	}
		
	public void removeTraining(Training training) {
		if(training==null) {
			throw new IllegalArgumentException("Training cannot be null");
		}
		if(this.trainings.contains(training)) {
			this.trainings.remove(training);
			training.setRoom(null);
		}
	}
}
