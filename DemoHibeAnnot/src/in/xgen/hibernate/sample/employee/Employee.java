package in.xgen.hibernate.sample.employee;
/**
 * 
 */

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * @author anilsharma
 *
 */
@Entity
@Table(name = "EMPLOYEE")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="employee_id_seq")
	@SequenceGenerator(name="employee_id_seq", sequenceName="employee_id_seq", allocationSize=1)
	@Column(name = "id")
	private int id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "salary")
	private int salary;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="employee")
	private Set<Certificate> certificates = new HashSet<Certificate>(0);
	
	public Employee() {
	}
	
	public Employee(String fname, String lname, int salary){
		this.firstName = fname;
		this.lastName = lname;
		this.salary = salary;
	}
	
	public Employee(String fname, String lname, int salary, Set<Certificate> certificates){
		this.firstName = fname;
		this.lastName = lname;
		this.salary = salary;
		this.certificates = certificates;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String first_name) {
		this.firstName = first_name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String last_name) {
		this.lastName = last_name;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Set<Certificate> getCertificates() {
		return this.certificates;
	}

	public void setCertificates(Set<Certificate> certificates) {
		this.certificates = certificates;
	}
}
