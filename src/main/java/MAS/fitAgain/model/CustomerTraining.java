package MAS.fitAgain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(
		uniqueConstraints=@UniqueConstraint(columnNames={"customerId", "trainingId"})
)
public class CustomerTraining {

	@Id
	@GeneratedValue
	private Long customerTrainingId;
	private Integer rate;
	@ManyToOne()
	@JoinColumn(
		name="trainingId",
		nullable=false
	)
	private Training training;
	@ManyToOne()
	@JoinColumn(
		name="customerId",
		nullable=false
	)
	private Customer customer;

	private CustomerTraining() {
	}
	
	public CustomerTraining(Training training, Customer customer, Integer rate) {
		setRate(rate);
		setCustomer(customer);
		setTraining(training);
	}

	public CustomerTraining(Training training, Customer customer) {
		this(training, customer, null);
	}
	
	@Max(5)
	@Min(1)
	public Integer getRate() {
		return rate;
	}
	
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	
	public void remove() {
		this.customer.removeTraining(this);
		this.training.removeCustomer(this);
		this.customer=null;
		this.training=null;
	}

	private void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

	private void setTraining(Training training) {
		this.training = training;
		training.addCustomer(this);
	}

	public Training getTraining() {
		return training;
	}
}