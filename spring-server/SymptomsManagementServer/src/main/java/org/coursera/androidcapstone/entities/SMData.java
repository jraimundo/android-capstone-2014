package org.coursera.androidcapstone.entities;

import java.util.List;

public class SMData {

	private List<Doctor> doctors;
	private List<Patient> patients;
	private List<Medication> medications;
	private List<PatientMedication> patientMedications;
	private List<Checkin> checkins;
	private List<Alerts> alerts;

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public List<Alerts> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<Alerts> alerts) {
		this.alerts = alerts;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public List<Medication> getMedications() {
		return medications;
	}

	public void setMedications(List<Medication> medications) {
		this.medications = medications;
	}

	public List<PatientMedication> getPatientMedications() {
		return patientMedications;
	}

	public void setPatientMedications(List<PatientMedication> patientMedications) {
		this.patientMedications = patientMedications;
	}

	public List<Checkin> getCheckins() {
		return checkins;
	}

	public void setCheckins(List<Checkin> checkins) {
		this.checkins = checkins;
	}

}
