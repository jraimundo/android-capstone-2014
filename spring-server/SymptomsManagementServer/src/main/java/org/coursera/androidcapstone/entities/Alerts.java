package org.coursera.androidcapstone.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "alerts")
public class Alerts {
	
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
	private Integer alertId;
    @Basic(optional = false)
    @Column(name = "patient_id")
    private Integer patientId;
	@Basic(optional = false)
	@Column(name = "acknowledged")
    private boolean acknowledged;
	
	public Alerts() {
	}
	
	public Alerts(Integer patientId) {
		this.patientId = patientId;
		this.acknowledged = false;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public boolean isAcknowledged() {
		return acknowledged;
	}

	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

	public Integer getAlertId() {
		return alertId;
	}

	public void setAlertId(Integer alertId) {
		this.alertId = alertId;
	}
	
}
