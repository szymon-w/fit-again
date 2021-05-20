package MAS.fitAgain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="personId")
public class Customer extends Person {

	@Column(unique=true)
	private String emailAdress;
	private String phoneNumber;
	private boolean subscriber;
	private int points;
	private static final int POINTSADDEDMONTHLY=10;
	
	@OneToMany(mappedBy="customer",
			cascade= {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH}
	)
	private Set<Appointment> appointments=new HashSet<>();
	@OneToMany(mappedBy="customer")
	private Set<CustomerTraining> trainings = new HashSet<CustomerTraining>();
	
	Customer() {
		super();
	}

	public Customer(String firstName, String lastName, String phoneNumber, String emailAdress) {
		super(firstName,lastName);
		setPhoneNumber(phoneNumber);
		setEmailAdress(emailAdress);
		setSubscriber(false);
		setPoints(0);
	}

	@NotBlank(message="Email cannot be blank")
	@Email
	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}
	
	@NotBlank
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isSubscriber() {
		return subscriber;
	}

	public void setSubscriber(boolean subscriber) {
		this.subscriber = subscriber;
	}

	@Min(0)
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
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
			appointment.setCustomer(this);
		}
	}
		
	public void removeAppointment(Appointment appointment) {
		if(appointment==null) {
			throw new IllegalArgumentException("Appointment cannot be null");
		}
		if(this.appointments.contains(appointment)) {
			this.appointments.remove(appointment);
			appointment.setCustomer(null);
		}
	}		
	

	public Set<CustomerTraining> getTrainings() {
		return new HashSet<CustomerTraining>(trainings);
	}


	public void addTraining(CustomerTraining training) {
		if (training.getCustomer() != this) {
			throw new IllegalArgumentException("Invalid training");
		}
		this.trainings.add(training);
	}

	public void removeTraining(CustomerTraining training) {
		if (training.getCustomer() != this) {
			throw new IllegalArgumentException("Invalid training");
		}
		this.trainings.remove(training);
	}

	public String getPersonalInfo() {
		return super.getFirstName()+" "+super.getLastName()+" "+emailAdress+" "+phoneNumber;
	}
	
	public void subscribe() {
		if(!isSubscriber())
			setSubscriber(true);
	}
	
	public void unsubcribe() {
		if(isSubscriber()) {
			setPoints(0);
			setSubscriber(false);
		}
	}
	
	public void buyAdditionalPoints(int number) {
		if(isSubscriber())
			setPoints(getPoints()+number);
	}
}
