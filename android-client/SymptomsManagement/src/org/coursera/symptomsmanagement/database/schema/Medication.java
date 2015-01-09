package org.coursera.symptomsmanagement.database.schema;

public class Medication {

	public static final String TABLE_NAME = "medication";
	public static final String ID = "medication_id";
	public static final String NAME = "medication_name";
	public static final String DESCRIPTION = "description";
	
	private Integer medicationId;
	private String medicationName;
	private String description;
	
	public Medication(Integer medicationId, String medicationName,
			String description) {
		this.medicationId = medicationId;
		this.medicationName = medicationName;
		this.description = description;
	}
	
	public Medication(Integer medicationId, String medicationName) {
		this.medicationId = medicationId;
		this.medicationName = medicationName;
	}
	
	public Medication(String medicationName, String description) {
		this.medicationName = medicationName;
		this.description = description;
	}
	
	
	public Integer getMedicationId() {
		return medicationId;
	}
	public void setMedicationId(Integer medicationId) {
		this.medicationId = medicationId;
	}
	public String getMedicationName() {
		return medicationName;
	}
	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
