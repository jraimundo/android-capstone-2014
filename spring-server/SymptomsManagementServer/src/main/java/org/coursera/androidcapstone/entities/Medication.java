/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coursera.androidcapstone.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author raimundo
 */
@Entity
@Table(name = "medication")
@NamedQueries({
    @NamedQuery(name = "Medication.findAll", query = "SELECT m FROM Medication m")})
public class Medication implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "medication_name")
    private String medicationName;
    @Column(name = "description")
    private String description;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "medication_id")
    private Integer medicationId;
//    @JoinTable(name = "patient_medication", joinColumns = {
//        @JoinColumn(name = "medication_id", referencedColumnName = "medication_id")}, inverseJoinColumns = {
//        @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")})
//    @ManyToMany
//    private List<Patient> patientList;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medication")
//    private List<CheckinMedication> checkinMedicationList;

    public Medication() {
    }

    public Medication(Integer medicationId) {
        this.medicationId = medicationId;
    }

    public Medication(Integer medicationId, String medicationName) {
        this.medicationId = medicationId;
        this.medicationName = medicationName;
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

    public Integer getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Integer medicationId) {
        this.medicationId = medicationId;
    }

//    public List<Patient> getPatientList() {
//        return patientList;
//    }
//
//    public void setPatientList(List<Patient> patientList) {
//        this.patientList = patientList;
//    }

//    public List<CheckinMedication> getCheckinMedicationList() {
//        return checkinMedicationList;
//    }
//
//    public void setCheckinMedicationList(List<CheckinMedication> checkinMedicationList) {
//        this.checkinMedicationList = checkinMedicationList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medicationId != null ? medicationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medication)) {
            return false;
        }
        Medication other = (Medication) object;
        if ((this.medicationId == null && other.medicationId != null) || (this.medicationId != null && !this.medicationId.equals(other.medicationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.coursera.androidcapstone.entities.Medication[ medicationId=" + medicationId + " ]";
    }
    
}
