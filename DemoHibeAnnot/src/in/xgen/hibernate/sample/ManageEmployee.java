package in.xgen.hibernate.sample;

import in.xgen.hibernate.sample.employee.Certificate;
import in.xgen.hibernate.sample.employee.Employee;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * 
 */

/**
 * @author anilsharma
 *
 */
public class ManageEmployee {
	private static SessionFactory factory;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			factory = new AnnotationConfiguration().configure().
			// addPackage("com.xyz") //add package if used.
					addAnnotatedClass(Employee.class).buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		ManageEmployee ME = new ManageEmployee();
		HashSet<Certificate> set1 = new HashSet();
	 set1.add(new Certificate("MCA"));
	 set1.add(new Certificate("MBA"));
	 set1.add(new Certificate("PMP"));

	 Integer emp1 = ME.addEmployee("Test1", "Cert1", 5000,set1);
	 
//	 Certificate cer= new Certificate("MCA");

//		Integer emp1 = ME.addEmployee("Test", "Cert", 10000,cer);
		/* List down all the employees */
/*		ME.listEmployees();

		ME.updateEmployee(emp1, 7000);
		System.out.println("------");
		ME.listEmployees();

		ME.deleteEmployee(emp1);
		System.out.println("--==--");
		ME.listEmployees();
*/
	}

	/* Method to CREATE an employee in the database */
	public Integer addEmployee(String fname, String lname, int salary) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;
		try {
			tx = session.beginTransaction();
			Employee employee = new Employee();
			employee.setFirstName(fname);
			employee.setLastName(lname);
			employee.setSalary(salary);
			employeeID = (Integer) session.save(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return employeeID;
	}
	
	public Integer addEmployee(String fname, String lname, int salary, Set<Certificate> certificates) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;
		try {
			tx = session.beginTransaction();
			Employee employee = new Employee();
			employee.setFirstName(fname);
			employee.setLastName(lname);
			employee.setSalary(salary);
			employeeID = (Integer) session.save(employee);
			System.out.println(" New employeeID: " + employeeID);
			for(Iterator<Certificate> iterCert = certificates.iterator(); iterCert.hasNext();){
				Certificate certificate = (Certificate) iterCert.next();
				certificate.setEmployee(employee);
				session.save(certificate);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return employeeID;
	}
	
	public Integer addEmployee(String fname, String lname, int salary, Certificate certificate) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;
		try {
			tx = session.beginTransaction();
			Employee employee = new Employee();
			employee.setFirstName(fname);
			employee.setLastName(lname);
			employee.setSalary(salary);
			employeeID = (Integer) session.save(employee);
			certificate.setEmployee(employee);
			employee.getCertificates().add(certificate);
			session.save(certificate);
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return employeeID;
	}

	/* Method to READ all the employees */
	public void listEmployees() {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String sql = "FROM Employee";
			// sql = "SELECT first_name, last_name FROM Employee";
			// SQLQuery query = session.createSQLQuery(sql);
			// query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			// List employees = query.list();
			List employees = session.createQuery(sql).list();
			for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
				Employee employee = (Employee) iterator.next();
				System.out.print("ID: " + employee.getId());
				System.out.print(" First Name: " + employee.getFirstName());
				System.out.print(" Last Name: " + employee.getLastName());
				System.out.println(" Salary: " + employee.getSalary());
			}
			/*
			 * for (Object object: employees) { Map employee = (Map) object;
			 * System.out.print("First Name: " + employee.get("first_name")); }
			 */
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* Method to UPDATE salary for an employee */
	public void updateEmployee(Integer EmployeeID, int salary) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class,
					EmployeeID);
			employee.setSalary(salary);
			session.update(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* Method to DELETE an employee from the records */
	public void deleteEmployee(Integer EmployeeID) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class,
					EmployeeID);
			session.delete(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
