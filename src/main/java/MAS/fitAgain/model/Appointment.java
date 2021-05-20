package MAS.fitAgain.model;

import java.time.*;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Appointment {

	public enum AppointmentStatus{booked,cancelled,finished}
	
	@Id
	@GeneratedValue
	private Long appointmentId;
	private LocalDate date;
	private LocalTime startTime;
	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;
	private String cancellationReason;
	private final static int DURATION = 60;
	
	@ManyToOne()
	@JoinColumn(
		name="CustomerId",
		nullable=false
	)
	private Customer customer;
	
	@ManyToOne()
	@JoinColumn(
		name="SlotId",
		nullable=false
	)
	private Slot slot;

	
	
	public Appointment() {
	}

	public Appointment(LocalDate date, LocalTime startTime, AppointmentStatus status, String cancellationReason) {
		setDate(date);
		setStartTime(startTime);
		setStatus(status);
		setCancellationReason(cancellationReason);
	}

	public Appointment(LocalDate date, LocalTime startTime, AppointmentStatus status) {
		this(date,startTime,status,"");
	}

	@NotNull
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@NotNull
	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	@NotNull
	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public String getCancellationReason() {
		return cancellationReason;
	}

	public void setCancellationReason(String cancellationReason) {
		this.cancellationReason = cancellationReason;
	}
		
	public LocalTime getFinishTime() {
		return startTime.plusMinutes(DURATION);
	}
	
	public static int getDuration() {
		return DURATION;
	}
	
	public void setCustomer(Customer customer) {
		if (customer != this.customer) {
			if (this.customer != null) {
				Customer temp = this.customer;
				this.customer = null;
				temp.removeAppointment(this);
			}
			if (customer != null) {
				this.customer = customer;
				customer.addAppointment(this);
			}
		}
	}

	public Customer getCustomer() {
		return customer;
	}

	
	public void setSlot(Slot slot) {
		if (slot != this.slot) {
			if (this.slot != null) {
				Slot temp = this.slot;
				this.slot = null;
				temp.removeAppointment(this);
			}
			if (slot != null) {
				this.slot = slot;
				slot.addAppointment(this);
			}
		}
	}

	public Slot getSlot() {
		return slot;
	}

	public static Appointment makeAppointment (Customer customer, Slot slot){
		Appointment appointment = new Appointment(slot.getDuty().getDate(),slot.getStartTime(),AppointmentStatus.booked);
		appointment.setCustomer(customer);
		appointment.setSlot(slot);
		return appointment;
	}
}
