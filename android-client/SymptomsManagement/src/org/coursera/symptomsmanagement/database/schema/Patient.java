package org.coursera.symptomsmanagement.database.schema;

public class Patient {
	
	public static final String TABLE_NAME = "patient";
	public static final String ID = "patient_id";
	public static final String MEDICAL_RECORD_NUMBER = "medical_record_number";
	public static final String FIRST_NAME = "first_name";
	public static final String LAST_NAME = "last_name";
	public static final String BIRTHDATE = "birthdate";
	
	private Integer patientId;
	private String medicalRecordNumber;
	private String firstName;
	private String lastName;
	private String birthdate;
	private Integer doctorId;
	
	public Patient(Integer patientId, String medicalRecordNumber,
			String firstName, String lastName, String birthdate) {
		this.patientId = patientId;
		this.medicalRecordNumber = medicalRecordNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
	}
	

	public Integer getPatientId() {
		return patientId;
	}
	
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	
	public String getMedicalRecordNumber() {
		return medicalRecordNumber;
	}
	
	public void setMedicalRecordNumber(String medicalRecordNumber) {
		this.medicalRecordNumber = medicalRecordNumber;
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
	
	public String getBirthdate() {
		return birthdate;
	}
	
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public Integer getDoctorId() {
		return doctorId;
	}
	
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
}
