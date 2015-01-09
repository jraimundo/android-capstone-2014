package org.coursera.androidcapstone.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.coursera.androidcapstone.entities.Alerts;
import org.coursera.androidcapstone.entities.Checkin;
import org.coursera.androidcapstone.entities.CheckinMedication;
import org.coursera.androidcapstone.entities.Doctor;
import org.coursera.androidcapstone.entities.Medication;
import org.coursera.androidcapstone.entities.Patient;
import org.coursera.androidcapstone.entities.PatientMedication;
import org.coursera.androidcapstone.entities.SMData;
import org.coursera.androidcapstone.repository.AlertsRepository;
import org.coursera.androidcapstone.repository.CheckinMedicationRepository;
import org.coursera.androidcapstone.repository.CheckinRepository;
import org.coursera.androidcapstone.repository.DoctorRepository;
import org.coursera.androidcapstone.repository.MedicationRepository;
import org.coursera.androidcapstone.repository.PatientMedicationRepository;
import org.coursera.androidcapstone.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

@Controller
public class SymptomsManagementController {

	@Autowired
	private DoctorRepository doctors;
	@Autowired
	private PatientRepository patients;
	@Autowired
	private MedicationRepository medications;
	@Autowired
	private CheckinMedicationRepository checkinMedications;
	@Autowired	
	private CheckinRepository checkins;
	@Autowired	
	private PatientMedicationRepository patientMedications;
	@Autowired	
	private AlertsRepository alerts;
	
	private Logger logger = Logger.getLogger(SymptomsManagementController.class.getName());
	
	@RequestMapping(value="/checkin", method=RequestMethod.POST)
	public @ResponseBody Integer getaAddCheckin(@RequestBody Checkin checkin) {
		Checkin savedCheckin = checkins.save(checkin);
		
		for (CheckinMedication cm : checkin.getCheckinMedicationList()) {
			logger.info("checkin:id " + cm.getCheckinId() + ", medicationId: "
					+ cm.getMedicationId() + ", token: " + cm.getMedicationToken());
			cm.setCheckinId(savedCheckin.getCheckinId());
			checkinMedications.save(cm);
		}
		
		checkPatientCondition(checkin.getPatientId(), checkin.getCheckinTimestamp().getTime());
		
		return savedCheckin.getCheckinId();
	}
	
	private void checkPatientCondition(Integer patientId, long checkinTimestamp) {
		int highestPainLevel = 3;
		long hour12interval = 12l*60l*60l*1000l;
		int highestEatDificultyLevel = 3;
		long hour16interval = 16l*60l*60l*1000l;
		
		List<Checkin> checkinList = checkins.findByPatientIdOrderByCheckinTimestampDesc(patientId);
		for (Checkin checkin : checkinList) {
			if (checkin.getThroatPainLevel() < highestPainLevel) {
				highestPainLevel = checkin.getThroatPainLevel();
			}
			
			if (checkin.getEatDificultyLevel() < highestEatDificultyLevel) {
				highestEatDificultyLevel = checkin.getEatDificultyLevel();
			}
			
			long timestamp = checkin.getCheckinTimestamp().getTime();
			if (checkinTimestamp - timestamp > hour12interval &&
					(highestPainLevel > 2 || highestEatDificultyLevel > 2)) {
				alerts.save(new Alerts(patientId));
				break;
			}
			
			if (checkinTimestamp - timestamp > hour16interval) {
				if (highestPainLevel > 1) {
					alerts.save(new Alerts(patientId));
				}
				break;
			}
		}
	}
	
	@RequestMapping(value="/alerts", method=RequestMethod.GET)
	public @ResponseBody List<Alerts> getAlerts(@RequestParam("doctorId") Integer doctorId) {
		List<Patient> doctorPatients = patients.findByDoctorId(doctorId);
		List<Alerts> alerts4Return = new ArrayList<Alerts>();
		
		for (Patient patient : doctorPatients) {
			List<Alerts> alerts_aux = Lists.newArrayList(alerts.findByPatientId(patient.getPatientId()));
			for (Alerts alert : alerts_aux) {
				if (!alert.isAcknowledged()) {
					alerts4Return.add(alert);
				}
			}
		}
		
		return alerts4Return;
	}
	
	@RequestMapping(value="/acknowledgeAlert", method=RequestMethod.POST)
	public @ResponseBody Boolean acknowledgeAlert(@RequestBody Integer alertId) {
		Alerts alert = alerts.findByAlertId(alertId);
		alert.setAcknowledged(true);
		alerts.save(alert);
		
		return true;
	}
	
	@RequestMapping(value="/doctor/patientsInfo", method=RequestMethod.GET)
	public @ResponseBody List<Checkin> getDoctorPatients(
			@RequestParam("doctorId") Integer doctorId, @RequestParam("ts") Long timestamp) {
		List<Checkin> result = new ArrayList<Checkin>();
		logger.info("Date: " + new Date(timestamp).toString());
		
		List<Patient> patientsList = patients.findByDoctorId(doctorId);
		for (Patient patient : patientsList) {
			List<Checkin> checkinList = checkins.findByPatientIdAndCheckinTimestampGreaterThan(
					patient.getPatientId(), new Date(timestamp));
			for (Checkin checkin : checkinList) {
				checkin.setCheckinMedicationList(checkinMedications.findByCheckinId(checkin.getCheckinId()));
			}
			
			result.addAll(checkinList);
		}
//		List<Checkin> result = checkins.findByCheckinTimestampGreaterThan(new Date(timestamp));
		
		return result;
	}
	
	@RequestMapping(value="/getAllData", method=RequestMethod.GET)
	public @ResponseBody SMData getAlldata() {
		SMData smData = new SMData();
		smData.setDoctors(Lists.newArrayList(doctors.findAll()));
		smData.setPatients(Lists.newArrayList(patients.findAll()));
		smData.setMedications(Lists.newArrayList(medications.findAll()));
		smData.setPatientMedications(Lists.newArrayList(patientMedications.findAll()));
		smData.setAlerts(Lists.newArrayList(alerts.findAll()));
		
		List<Checkin> checkinsList = Lists.newArrayList(checkins.findAll());
		for (Checkin checkin : checkinsList) {
			checkin.setCheckinMedicationList(checkinMedications.findByCheckinId(checkin.getCheckinId()));
		}
		
		smData.setCheckins(checkinsList);
		
		return smData;
	}
	
	@RequestMapping(value="/doctor/addPatientMedication", method=RequestMethod.POST)
	public @ResponseBody boolean addPatientMedication(@RequestBody PatientMedication patientMedication) {
		
		patientMedications.save(patientMedication);
		
		return true;
	}
	
	@RequestMapping(value="/doctor/removePatientMedication", method=RequestMethod.POST)
	public @ResponseBody boolean removePatientMedication(@RequestBody PatientMedication patientMedication) {
		
		Integer patientMedicationId = patientMedications
				.findByPatientIdAndMedicationId(patientMedication.getPatientId(), patientMedication.getMedicationId())
				.getId();
		
		patientMedications.delete(patientMedicationId);
		
		return true;
	}
	
	@RequestMapping(value="/doctor", method=RequestMethod.POST)
	public @ResponseBody boolean addDoctor(Doctor doctor) {
		
		logger.info("Doctor POST method!");
		
		doctors.save(doctor);
		return true;
	}
	
	@RequestMapping(value="/doctor", method=RequestMethod.GET)
	public @ResponseBody Collection<Doctor> getDoctorList() {
		logger.info("Doctor GET method!");
		
		List<Doctor> doctorsList = Lists.newArrayList(doctors.findAll());
		
//		for (Doctor doctor : doctorsList) {
//			doctor.setPatientCollection(null);
//		}
		
		return doctorsList;
	}
	
	@RequestMapping(value="/patient", method=RequestMethod.GET)
	public @ResponseBody Collection<Patient> getPatientList() {
		return Lists.newArrayList(patients.findAll());
	}
	
	@RequestMapping(value="/medication", method=RequestMethod.GET)
	public @ResponseBody Collection<Medication> getMedicationList() {
		return Lists.newArrayList(medications.findAll());
	}
	
//	@RequestMapping(value="/checkin_medication", method=RequestMethod.GET)
//	public @ResponseBody Collection<CheckinMedication> getCheckinMedicationList() {
//		return Lists.newArrayList(checkinMedications.findAll());
//	}
	
	@RequestMapping(value="/checkin", method=RequestMethod.GET)
	public @ResponseBody Collection<Checkin> getCheckinList() {
		return Lists.newArrayList(checkins.findAll());
	}
}
