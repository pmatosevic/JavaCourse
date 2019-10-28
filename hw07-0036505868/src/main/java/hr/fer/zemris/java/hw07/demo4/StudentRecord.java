package hr.fer.zemris.java.hw07.demo4;

/**
 * Represents a student record.
 * 
 * @author Patrik
 *
 */
public class StudentRecord {

	/**
	 * Student JMBAG
	 */
	private String jmbag;
	
	/**
	 * Student last name
	 */
	private String lastName;
	
	/**
	 * Student first name
	 */
	private String firstName;
	
	/**
	 * Points on midterm exam
	 */
	private double midtermPoints;
	
	/**
	 * Point on final exam
	 */
	private double finalPoints;
	
	/**
	 * Point on laboratory exercises
	 */
	private double labsPoints;
	
	private int grade;

	/**
	 * @param jmbag jmbag
	 * @param lastName last name
	 * @param firstName first name
	 * @param midtermPoints midterm points
	 * @param finalPoints finals ponts
	 * @param labsPoints lab points
	 * @param grade grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double midtermPoints, double finalPoints,
			double labsPoints, int grade) {
		if (grade < 1 || grade > 5) {
			throw new IllegalArgumentException("Invalid grade: " + grade);
		}
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.midtermPoints = midtermPoints;
		this.finalPoints = finalPoints;
		this.labsPoints = labsPoints;
		this.grade = grade;
	}

	/**
	 * @return the jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the midterm points
	 */
	public double getMidtermPoints() {
		return midtermPoints;
	}

	/**
	 * @return the final points
	 */
	public double getFinalPoints() {
		return finalPoints;
	}

	/**
	 * @return the lab points
	 */
	public double getLabsPoints() {
		return labsPoints;
	}

	/**
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}
	
	/**
	 * @return the sum of all points
	 */
	public double sumPoints() {
		return midtermPoints + finalPoints + labsPoints;
	}
	
	@Override
	public String toString() {
		return String.format("%s\t%s\t%s\t%f\t%f\t%f\t%d", 
				jmbag, lastName, firstName, midtermPoints, finalPoints, labsPoints, grade);
	}
	
	
	
	
}
