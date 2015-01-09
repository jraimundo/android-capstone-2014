package org.coursera.symptomsmanagement.database.schema;

public class Doctor {

	public static final String TABLE_NAME = "doctor";
	public static final String ID = "doctor_id";
	public static final String FIRST_NAME = "first_name";
	public static final String LAST_NAME = "last_name";
	
	private Integer doctorId;
	private String firstName;
	private String lastName;
	
	public Integer getDoctorId() {
		return doctorId;
	}
	
	
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
