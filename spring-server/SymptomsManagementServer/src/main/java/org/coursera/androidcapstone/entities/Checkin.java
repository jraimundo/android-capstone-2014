/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coursera.androidcapstone.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author raimundo
 */
@Entity
@Table(name = "checkin")
@NamedQueries({
    @NamedQuery(name = "Checkin.findAll", query = "SELECT c FROM Checkin c")})
public class Checkin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "checkin_id")
    private Integer checkinId;
    @Basic(optional = false)
    @Column(name = "throat_pain_level")
    private int throatPainLevel;
    @Basic(optional = false)
    @Column(name = "eat_dificulty_level")
    private int eatDificultyLevel;
    @Column(name = "checkin_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkinTimestamp;
//    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
//    @ManyToOne
//    private Patient patientId;
    @Column(name = "patient_id")
    private Integer patientId;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "checkin")
    @Transient
    private List<CheckinMedication> checkinMedicationList;

    public Checkin() {
    }

    public Checkin(Integer checkinId) {
        this.checkinId = checkinId;
    }

    public Checkin(Integer checkinId, int throatPainLevel, int eatDificultyLevel) {
        this.checkinId = checkinId;
        this.throatPainLevel = throatPainLevel;
        this.eatDificultyLevel = eatDificultyLevel;
    }

    public Integer getCheckinId() {
        return checkinId;
    }

    public void setCheckinId(Integer checkinId) {
        this.checkinId = checkinId;
    }

    public int getThroatPainLevel() {
        return throatPainLevel;
    }

    public void setThroatPainLevel(int throatPainLevel) {
        this.throatPainLevel = throatPainLevel;
    }

    public int getEatDificultyLevel() {
        return eatDificultyLevel;
    }

    public void setEatDificultyLevel(int eatDificultyLevel) {
        this.eatDificultyLevel = eatDificultyLevel;
    }

    public Date getCheckinTimestamp() {
        return checkinTimestamp;
    }

    public void setCheckinTimestamp(Date checkinTimestamp) {
        this.checkinTimestamp = checkinTimestamp;
    }

//    public Patient getPatientId() {
//        return patientId;
//    }
//
//    public void setPatientId(Patient patientId) {
//        this.patientId = patientId;
//    }

    public List<CheckinMedication> getCheckinMedicationList() {
        return checkinMedicationList;
    }

    public void setCheckinMedicationList(List<CheckinMedication> checkinMedicationList) {
        this.checkinMedicationList = checkinMedicationList;
    }

    public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (checkinId != null ? checkinId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Checkin)) {
            return false;
        }
        Checkin other = (Checkin) object;
        if ((this.checkinId == null && other.checkinId != null) || (this.checkinId != null && !this.checkinId.equals(other.checkinId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.coursera.androidcapstone.entities.Checkin[ checkinId=" + checkinId + " ]";
    }
    
}
