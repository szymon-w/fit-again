package MAS.fitAgain.model;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class Slot {
	
	@Id
	@GeneratedValue
	private Long slotId;
	private LocalTime startTime;
	private boolean free;
	@ManyToOne()
	@JoinColumn(
		name="dutyId",
		nullable=false
	)
	private Duty duty;
	
	@OneToMany(mappedBy="slot")
	private Set<Appointment> appointments=new HashSet<>();
	
	Slot() {
	}

	public Slot(LocalTime startTime, boolean free) {
		setStartTime(startTime);
		setFree(free);
	}

	@NotNull(message = "Time cannot be null")
	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	
	public Duty getDuty() {
		return duty;
	}

	public void setDuty(Duty duty) {
		if (duty != this.duty) {
			if (this.duty != null) {
				Duty temp = this.duty;
				this.duty = null;
				temp.removeSlot(this);
			}
			if (duty != null) {
				this.duty = duty;
				duty.addSlot(this);
			}
		}
	}
	
	
	public Set<Appointment> getAppointments() {
		return new HashSet<Appointment>(this.appointments);
	}
		
	public void addAppointment(Appointment appointment) {
		if(appointment==null) {
			throw new IllegalArgumentException("Appointment cannot be null");
		}
		if(!this.appointments.contains(appointment)) {
			this.appointments.add(appointment);
			appointment.setSlot(this);
		}
	}
		
	public void removeAppointment(Appointment appointment) {
		if(appointment==null) {
			throw new IllegalArgumentException("Appointment cannot be null");
		}
		if(this.appointments.contains(appointment)) {
			this.appointments.remove(appointment);
			appointment.setSlot(null);
		}
	}

	public void setOccupied(){
		setFree(false);
		duty.updateAvaliability();
	}

	@Override
	public String toString() {
		return startTime.toString();
	}
}
