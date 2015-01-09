/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coursera.androidcapstone.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author raimundo
 */
@Entity
@Table(name = "checkin_medication")
@NamedQueries({
    @NamedQuery(name = "CheckinMedication.findAll", query = "SELECT c FROM CheckinMedication c")})
public class CheckinMedication implements Serializable {
    private static final long serialVersionUID = 1L;
//    @EmbeddedId
//    protected CheckinMedicationPK checkinMedicationPK;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "checkin_id")
    private Integer checkinId;
    @Column(name = "medication_id")
    private Integer medicationId;
    @Basic(optional = false)
    @Column(name = "medication_token")
    private boolean medicationToken;
//    @JoinColumn(name = "medication_id", referencedColumnName = "medication_id", insertable = false, updatable = false)
//    @ManyToOne(optional = false)
//    private Medication medication;
//    @Column(name = "medication_id")
//    private Integer medicationId;
//    @JoinColumn(name = "checkin_id", referencedColumnName = "checkin_id", insertable = false, updatable = false)
//    @ManyToOne(optional = false)
//    private Checkin checkin;
//    @Column(name = "checkin_id")
//    private Integer checkinId;

    public CheckinMedication() {
    }

//    public CheckinMedication(CheckinMedicationPK checkinMedicationPK) {
//        this.checkinMedicationPK = checkinMedicationPK;
//    }
//
//    public CheckinMedication(CheckinMedicationPK checkinMedicationPK, boolean medicationToken) {
//        this.checkinMedicationPK = checkinMedicationPK;
//        this.medicationToken = medicationToken;
//    }
//
//    public CheckinMedication(int checkinId, int medicationId) {
//        this.checkinMedicationPK = new CheckinMedicationPK(checkinId, medicationId);
//    }
//
//    public CheckinMedicationPK getCheckinMedicationPK() {
//        return checkinMedicationPK;
//    }
//
//    public void setCheckinMedicationPK(CheckinMedicationPK checkinMedicationPK) {
//        this.checkinMedicationPK = checkinMedicationPK;
//    }

    public boolean getMedicationToken() {
        return medicationToken;
    }

    public void setMedicationToken(boolean medicationToken) {
        this.medicationToken = medicationToken;
    }

	public Integer getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Integer checkinId) {
		this.checkinId = checkinId;
	}

	public Integer getMedicationId() {
		return medicationId;
	}

	public void setMedicationId(Integer medicationId) {
		this.medicationId = medicationId;
	}

//    public Medication getMedication() {
//        return medication;
//    }
//
//    public void setMedication(Medication medication) {
//        this.medication = medication;
//    }
//
//    public Checkin getCheckin() {
//        return checkin;
//    }
//
//    public void setCheckin(Checkin checkin) {
//        this.checkin = checkin;
//    }

//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (checkinMedicationPK != null ? checkinMedicationPK.hashCode() : 0);
//        return hash;
//    }

//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof CheckinMedication)) {
//            return false;
//        }
//        CheckinMedication other = (CheckinMedication) object;
//        if ((this.checkinMedicationPK == null && other.checkinMedicationPK != null) || (this.checkinMedicationPK != null && !this.checkinMedicationPK.equals(other.checkinMedicationPK))) {
//            return false;
//        }
//        return true;
//    }

//    @Override
//    public String toString() {
//        return "org.coursera.androidcapstone.entities.CheckinMedication[ checkinMedicationPK=" + checkinMedicationPK + " ]";
//    }
    
}
