package in.xgen.hibernate.sample.employee;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the certificate database table.
 * 
 */
@Entity
@Table(name = "CERTIFICATE")
public class Certificate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CERTIFICATE_ID_GENERATOR", sequenceName = "CERTIFICATE_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CERTIFICATE_ID_GENERATOR")
	private Integer id;

	@Column(name = "certificate_name")
	private String certificateName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;

	public Certificate() {
	}

	public Certificate(String name) {
		this.certificateName = name;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCertificateName() {
		return this.certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((certificateName == null) ? 0 : certificateName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Certificate other = (Certificate) obj;
		if (certificateName == null) {
			if (other.certificateName != null)
				return false;
		} else if (!certificateName.equals(other.certificateName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}