package hr.fer.zemris.java.hw05.db;

import java.util.Objects;


/**
 * Represents a record with student's data in the students database.
 * @author Patrik
 *
 */
public class StudentRecord {

	/**
	 * Minimal grade
	 */
	public static final int MIN_GRADE = 1;

	/**
	 * Maximal grade
	 */
	public static final int MAX_GRADE = 5;

	/**
	 * Student's JMBAG
	 */
	private String jmbag;
	
	/**
	 * Student's last name
	 */
	private String lastName;
	
	/**
	 * Student's first name
	 */
	private String firstName;
	
	/**
	 * Student's final grade
	 */
	private int finalGrade;

	/**
	 * Creates a new {@code StudentRecord}
	 * @param jmbag JMBAG
	 * @param lastName last name
	 * @param firstName first name
	 * @param finalGrade final grade
	 * @throws IllegalArgumentException if the grade is not in range [1, 5]
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		if (finalGrade < MIN_GRADE || finalGrade > MAX_GRADE) {
			throw new IllegalArgumentException("Grade should be in range [1, 5].");
		}
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	
	/**
	 * Returns the JMBAG
	 * @return the JMBAG
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns last name
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns first name
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns final grade
	 * @return final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}




	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}
	
	@Override
	public String toString() {
		return jmbag + " " + firstName + " " + lastName;
	}
	
	
	
}
