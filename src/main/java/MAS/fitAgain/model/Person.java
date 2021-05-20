package MAS.fitAgain.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table
@Inheritance(strategy=InheritanceType.JOINED)

public abstract class Person {

	@Id
	@GeneratedValue
	private Long personId;
	private String firstName;
	private String lastName;
	
	Person() {
	}
	
	protected Person(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	@NotBlank(message="FirstName cannot be blank")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@NotBlank(message="LastName cannot be blank")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public abstract String getPersonalInfo();

	public Long getPersonId() {
		return personId;
	}

	@Override
	public String toString() {
		return 	firstName +" "+ lastName ;
	}
}
