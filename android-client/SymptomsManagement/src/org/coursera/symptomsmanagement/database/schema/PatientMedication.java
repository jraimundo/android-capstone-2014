package org.coursera.symptomsmanagement.database.schema;

public class PatientMedication {

	public static final String TABLE_NAME = "patient_medication";

	private Integer patientId;
	private Integer medicationId;

	public PatientMedication(Integer patientId, Integer medicationId) {
		super();
		this.patientId = patientId;
		this.medicationId = medicationId;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public Integer getMedicationId() {
		return medicationId;
	}

	public void setMedicationId(Integer medicationId) {
		this.medicationId = medicationId;
	}
}
