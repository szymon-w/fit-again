package MAS.fitAgain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="personId")
public abstract class Employee extends Person {

	@Column(unique=true)
	private int idNumber;
	private LocalDate birthDate;
	private boolean B2BPartner;
	private BigDecimal hourlyRate;
	private BigDecimal hoursWorked;
	private BigDecimal salaryBasis;
	private final static BigDecimal BONUS=new BigDecimal(1000);
	
	Employee() {
		super();
	}

	public Employee(int idNumber, String firstName, String lastName, LocalDate birthDate, boolean b2bPartner, BigDecimal hourlyRate,
			BigDecimal salaryBasis) {
		super(firstName,lastName);
		setIdNumber(idNumber);
		setBirthDate(birthDate);
		setB2BPartner(b2bPartner);
		setHoursWorked(new BigDecimal(0));
		if(b2bPartner) {
			setHourlyRate(hourlyRate);
			setSalaryBasis(new BigDecimal(0));
		} else {
			setHourlyRate(new BigDecimal(0));
			setSalaryBasis(salaryBasis);
		}
	}

	@Min(1)
	public int getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}

	@NotNull
	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public boolean isB2BPartner() {
		return B2BPartner;
	}

	public void setB2BPartner(boolean b2bPartner) {
		B2BPartner = b2bPartner;
	}

	@NotNull
	@Min(0)
	public BigDecimal getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(BigDecimal hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	@NotNull
	public BigDecimal getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(BigDecimal hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	@NotNull
	@Min(0)
	public BigDecimal getSalaryBasis() {
		return salaryBasis;
	}

	public void setSalaryBasis(BigDecimal salaryBasis) {
		this.salaryBasis = salaryBasis;
	}
	
	public String getPersonalInfo() {
		return super.getFirstName()+" "+super.getLastName()+" "+birthDate;
	}
	
	public BigDecimal getSalary() {
		if(B2BPartner) {
			return hoursWorked.multiply(hourlyRate);
		}else {
			return salaryBasis.add(BONUS);
		}
	}
}
