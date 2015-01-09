package org.coursera.androidcapstone.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "patient_medication")
public class PatientMedication implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
	@Column(name = "patient_id")
	@Basic(optional = false)
    private Integer patientId;
	@Basic(optional = false)
    @Column(name = "medication_id")
    private Integer medicationId;
    
	public PatientMedication() {
	}
	
	public PatientMedication(Integer patientId, Integer medicationId) {
		super();
		this.patientId = patientId;
		this.medicationId = medicationId;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
