package org.coursera.symptomsmanagement.database.schema;

import java.util.List;

public class Checkin {
	
	public static final String TABLE_NAME = "checkin";
	public static final String ID = "checkin_id";
	public static final String THROAT_PAIN_LEVEL = "throat_pain_level";
	public static final String EAT_DIFICULTY_LEVEL = "eat_dificulty_level";
	public static final String CHECKIN_TIMESTAMP = "checkin_timestamp";

	private Integer checkinId;
	private Integer throatPainLevel;
	private Integer eatDificultyLevel;
	private Long checkinTimestamp;
	private Integer patientId;
	private List<CheckinMedication> checkinMedicationList;

	
	public Integer getEatDificultyLevel() {
		return eatDificultyLevel;
	}

	public void setEatDificultyLevel(Integer eatDificultyLevel) {
		this.eatDificultyLevel = eatDificultyLevel;
	}

	public List<CheckinMedication> getCheckinMedicationList() {
		return checkinMedicationList;
	}

	public void setCheckinMedicationList(
			List<CheckinMedication> checkinMedicationList) {
		this.checkinMedicationList = checkinMedicationList;
	}

	public Integer getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Integer checkinId) {
		this.checkinId = checkinId;
	}

	public Integer getThroatPainLevel() {
		return throatPainLevel;
	}

	public void setThroatPainLevel(Integer throatPainLevel) {
		this.throatPainLevel = throatPainLevel;
	}

	public Long getCheckinTimestamp() {
		return checkinTimestamp;
	}

	public void setCheckinTimestamp(Long checkinTimestamp) {
		this.checkinTimestamp = checkinTimestamp;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
}
