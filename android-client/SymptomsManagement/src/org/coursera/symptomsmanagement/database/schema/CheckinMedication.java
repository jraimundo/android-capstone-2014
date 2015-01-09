package org.coursera.symptomsmanagement.database.schema;

public class CheckinMedication {
	
	public static final String TABLE_NAME = "checkin_medication";
	public static final String MEDICATION_TOKEN = "medication_token";

	private Integer medicationId;
	private Boolean medicationToken;
	
	public CheckinMedication(Integer medicationId, boolean medicationToken) {
		this.medicationId = medicationId;
		this.medicationToken = medicationToken;
	}

	public Integer getMedicationId() {
		return medicationId;
	}
	
	public void setMedicationId(Integer medicationId) {
		this.medicationId = medicationId;
	}
	
	public Boolean getMedicationToken() {
		return medicationToken;
	}
	
	public void setMedicationToken(Boolean medicationToken) {
		this.medicationToken = medicationToken;
	}
	
}
