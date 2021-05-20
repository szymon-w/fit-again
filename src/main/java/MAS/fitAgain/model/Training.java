package MAS.fitAgain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
public class Training {

	@Id
	@GeneratedValue
	private Long trainingId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private String additionalInfo;
	@ManyToOne()
	@JoinColumn(
		name="PersonId",
		nullable=true
	)
	private Trainer trainer;
	@ManyToOne()
	@JoinColumn(
		name="RoomId",
		nullable=true
	)
	private Room room;
	@ManyToMany()
	@JoinTable(
		name="TrainingTrainingType",
		joinColumns=@JoinColumn(name="trainingId"),
		inverseJoinColumns=@JoinColumn(name="trainingTypeId")
	)
	@Size(min=1)
	private Set<TrainingType> trainingTypes=new HashSet<>();

	@OneToMany(mappedBy="training")
	@Size(max=10)
	private Set<CustomerTraining> customers = new HashSet<>();
	@ManyToMany()
	@JoinTable(
		name="TrainingTrainingEquipment",
		joinColumns=@JoinColumn(name="trainingId"),
		inverseJoinColumns=@JoinColumn(name="trainingEquipmentId")
	)
	private Set<TrainingEquipment> trainingEquipments = new HashSet<>();

	public Training() {
	}
	
	public Training(LocalDate date, LocalTime startTime, LocalTime endTime) {
		this.setDate(date);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}

	public Training(LocalDate date, LocalTime startTime, LocalTime endTime, String addInfo) {
		this(date, startTime, endTime);
		this.additionalInfo = addInfo;
	}

	@NotNull(message="Date cannot be null")
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
			this.date = date;
	}

	@NotNull(message="Start time cannot be null")
	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	@NotNull(message="End time cannot be null")
	public LocalTime getEndTime() {
		return endTime;
	}
	
	public void setEndTime(LocalTime endTime) {
		if (endTime.isBefore(this.startTime)) {
			throw new IllegalArgumentException("Training cannot end before start");
		} else {
			this.endTime = endTime;
		}
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public int getDuration() {
		return (int) this.startTime.until(this.endTime, ChronoUnit.MINUTES);
	}

	public void setTrainer(Trainer trainer) {
		if (trainer != this.trainer) {
			if (this.trainer != null) {
				Trainer temp = this.trainer;
				this.trainer = null;
				temp.removeTraining(this);
			}
			if (trainer != null) {
				this.trainer = trainer;
				trainer.addTraining(this);
			}
		}
	}

	public Trainer getTrainer() {
		return trainer;
	}

	
	public void setRoom(Room room) {
		if (room != this.room) {
			if (this.room != null) {
				Room temp = this.room;
				this.room = null;
				temp.removeTraining(this);
			}
			if (room != null) {
				this.room = room;
				room.addTraining(this);
			}
		}
	}

	public Room getRoom() {
		return room;
	}
	
	public void addTrainingType(TrainingType trainingType) {
		if(trainingType==null) {
			throw new IllegalArgumentException("TrainingType cannot be null");
		}
		if(!this.trainingTypes.contains(trainingType)) {
			this.trainingTypes.add(trainingType);
			trainingType.addTraining(this);
		}
	}

	public void removeTrainingType(TrainingType trainingType) {
		if(trainingType==null) {
			throw new IllegalArgumentException("TrainingType cannot be null");
		}
		if(this.trainingTypes.contains(trainingType)) {
			this.trainingTypes.remove(trainingType);
			trainingType.removeTraining(this);
		}
	}

	
	public Set<TrainingEquipment> getTrainingEquipments() {
		return new HashSet<TrainingEquipment>(this.trainingEquipments);
	}
		
	public void addTrainingEquipment(TrainingEquipment trainingEquipment) {
		if(trainingEquipment==null) {
			throw new IllegalArgumentException("TrainingEquipment cannot be null");
		}
		if(!this.trainingEquipments.contains(trainingEquipment)) {
			this.trainingEquipments.add(trainingEquipment);
			trainingEquipment.addTraining(this);
		}
	}
		
	public void removeTrainingEquipment(TrainingEquipment trainingEquipment) {
		if(trainingEquipment==null) {
			throw new IllegalArgumentException("TrainingEquipment cannot be null");
		}
		if(this.trainingEquipments.contains(trainingEquipment)) {
			this.trainingEquipments.remove(trainingEquipment);
			trainingEquipment.removeTraining(this);
		}
	}

	public Set<CustomerTraining> getCustomers() {
		return new HashSet<CustomerTraining>(customers);
	}

	public void addCustomer(CustomerTraining customer) {
		if (customer.getTraining() != this) {
			throw new IllegalArgumentException("Invalid customer");
		}
		this.customers.add(customer);
	}

	public void removeCustomer(CustomerTraining customer) {
		if (customer.getTraining() != this) {
			throw new IllegalArgumentException("Invalid customer");
		}
		this.customers.remove(customer);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Date: " + this.date.toString() + ", ");
		sb.append("Start: " + this.startTime.toString() + ", ");
		sb.append("End: " + this.endTime.toString() + ", ");
		sb.append("Duration: " + getDuration() + " min, ");
		if (!(this.additionalInfo == null)) {
			sb.append(", Additional info: " + this.additionalInfo + ", ");
		}
		return sb.toString();
	}
}

	

