package MAS.fitAgain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Duty {

	@Id
	@GeneratedValue
	private Long dutyId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime finishTime;
	private boolean avaliable;	

	@ManyToOne()
	@JoinColumn(
		name="roomId",
		nullable=true
	)
	private Room room;

	@OneToMany(mappedBy="duty",
			cascade= {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH}
	)
	private Set<Slot> slots=new HashSet<Slot>();

	@ManyToOne()
	@JoinColumn(
		name="personId",
		nullable=false
	)
	private Masseur masseur;
	
	public Duty() {
	}
	
	public Duty(LocalDate date, LocalTime startTime, LocalTime finishTime, boolean avaliable) {
		super();
		setDate(date);
		setStartTime(startTime);
		setFinishTime(finishTime);
		setAvaliable(avaliable);
	}

	@NotNull(message = "Date cannot be null")
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}

	@NotNull(message = "Time cannot be null")
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	
	@NotNull(message = "Time cannot be null")
	public LocalTime getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(LocalTime finishTime) {
		if (finishTime.isBefore(this.startTime)) {
			throw new IllegalArgumentException("Duty cannot finish before start");
		}
		this.finishTime = finishTime;
	}
	
	public boolean isAvaliable() {
		return avaliable;
	}
	public void setAvaliable(boolean avaliable) {
		this.avaliable = avaliable;
	}
	
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		if (room != this.room) {
			if (this.room != null) {
				Room temp = this.room;
				this.room = null;
				temp.removeDuty(this);
			}
			if (room != null) {
				this.room = room;
				room.addDuty(this);
			}
		}
	}
	
	
	public Set<Slot> getSlots() {
		return new HashSet<Slot>(slots);
	}	
	
	public void addSlot(Slot slot) {
		if(slot==null) {
			throw new IllegalArgumentException("Slot cannot be null");
		}
		if(!this.slots.contains(slot)) {
			this.slots.add(slot);
			slot.setDuty(this);
		}
	}
	
	public void removeSlot(Slot slot) {
		if(slot==null) {
			throw new IllegalArgumentException("Slot cannot be null");
		}
		if(this.slots.contains(slot)) {
			this.slots.remove(slot);
			slot.setDuty(null);
		}
	}
	
	public void setMasseur(Masseur masseur) {
		if (masseur != this.masseur) {
			if (this.masseur != null) {
				Masseur temp = this.masseur;
				this.masseur = null;
				temp.removeDuty(this);
			}
			if (masseur != null) {
				this.masseur = masseur;
				masseur.addDuty(this);
			}
		}
	}

	public Masseur getMasseur() {
		return masseur;
	}

	public List<Slot> getFreeSlots(){
		List<Slot> freeSlots=new ArrayList<>();
		for(Slot slot:slots){
			if(slot.isFree()){
				freeSlots.add(slot);
			}
		}
		return freeSlots;
	}

	public void updateAvaliability(){
		for(Slot slot:slots){
			if(slot.isFree()){
				setAvaliable(true);
				return;
			}
		}
		setAvaliable(false);
	}


}