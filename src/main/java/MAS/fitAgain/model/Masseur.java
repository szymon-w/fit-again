package MAS.fitAgain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@PrimaryKeyJoinColumn(referencedColumnName="personId")
public class Masseur extends Employee {

	private String businessInfo;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="courses",joinColumns = @JoinColumn(name = "masseur_id"))
	@Column(name="course",nullable=false)
	private Set<String> finishedCourses = new HashSet<String>();

	@OneToMany(mappedBy="masseur",
		cascade= {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH}
	)
	private Set<Duty> duties=new HashSet<>();
	
	Masseur() {
		super();
	}

	public Masseur(int idNumber, String firstName, String lastName, LocalDate birthDate, boolean b2bPartner, BigDecimal hourlyRate,
			BigDecimal salaryBasis, String businessInfo) {
		super(idNumber, firstName,lastName, birthDate, b2bPartner,hourlyRate,salaryBasis);
		setBusinessInfo(businessInfo);
	}

	public Masseur(int idNumber, String firstName, String lastName, LocalDate birthDate, boolean b2bPartner, BigDecimal hourlyRate,
			BigDecimal salaryBasis, String businessInfo, String finishedCourse) {
		this(idNumber, firstName,lastName, birthDate, b2bPartner,hourlyRate,salaryBasis,businessInfo);
		addFinishedCourse(finishedCourse);
	}



	@NotBlank
	public String getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(String businessInfo) {
		this.businessInfo = businessInfo;
	}

	
	public Set<@NotBlank String> getFinishedCourses() {
		return finishedCourses;
	}

	public void addFinishedCourse(String type) {
		this.finishedCourses.add(type);
	}

	public void removeType(String type) {
		this.finishedCourses.remove(type);
	}

	public Set<Duty> getDuties() {
		return new HashSet<Duty>(this.duties);
	}
		
	public void addDuty(Duty duty) {
		if(duty==null) {
			throw new IllegalArgumentException("Duty cannot be null");
		}
		if(!this.duties.contains(duty)) {
			this.duties.add(duty);
			duty.setMasseur(this);
		}
	}
		
	public void removeDuty(Duty duty) {
		if(duty==null) {
			throw new IllegalArgumentException("Duty cannot be null");
		}
		if(this.duties.contains(duty)) {
			this.duties.remove(duty);
			duty.setMasseur(null);
		}
	}

	public Set<LocalDate> getAvailableDates(){
		Set<LocalDate> availableDates=new HashSet<>();
		for(Duty duty:duties){
			if(duty.isAvaliable()){
				availableDates.add(duty.getDate());
			}
		}
		return availableDates;
	}

	public List<Slot> getFreeSlots(LocalDate date){
		List<Slot> freeSlots = new ArrayList<>();
		for(Duty duty:duties){
			if(duty.getDate().equals(date)){
				freeSlots.addAll(duty.getFreeSlots());
			}
		}
		return freeSlots;
	}
}
