/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coursera.androidcapstone.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author raimundo
 */
@Embeddable
public class CheckinMedicationPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "checkin_id")
    private int checkinId;
    @Basic(optional = false)
    @Column(name = "medication_id")
    private int medicationId;

    public CheckinMedicationPK() {
    }

    public CheckinMedicationPK(int checkinId, int medicationId) {
        this.checkinId = checkinId;
        this.medicationId = medicationId;
    }

    public int getCheckinId() {
        return checkinId;
    }

    public void setCheckinId(int checkinId) {
        this.checkinId = checkinId;
    }

    public int getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(int medicationId) {
        this.medicationId = medicationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) checkinId;
        hash += (int) medicationId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CheckinMedicationPK)) {
            return false;
        }
        CheckinMedicationPK other = (CheckinMedicationPK) object;
        if (this.checkinId != other.checkinId) {
            return false;
        }
        if (this.medicationId != other.medicationId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.coursera.androidcapstone.entities.CheckinMedicationPK[ checkinId=" + checkinId + ", medicationId=" + medicationId + " ]";
    }
    
}
