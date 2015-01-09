package org.coursera.symptomsmanagement.database.schema;

public class Alerts {

	public static final String ID = "alert_id";
	
	private Integer alertId;
	private Integer patientId;
	private Boolean acknowledged;

	public Integer getAlertId() {
		return alertId;
	}

	public void setAlertId(Integer alertId) {
		this.alertId = alertId;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public Boolean isAcknowledged() {
		return acknowledged;
	}

	public void setAcknowledged(Boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

}
