package org.coursera.symptomsmanagement.client;

import java.util.List;

import org.coursera.symptomsmanagement.database.schema.Alerts;
import org.coursera.symptomsmanagement.database.schema.Checkin;
import org.coursera.symptomsmanagement.database.schema.Doctor;
import org.coursera.symptomsmanagement.database.schema.PatientMedication;
import org.coursera.symptomsmanagement.database.schema.SMData;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface RestClient {
	
	public static final String endpoint = "http://178.62.10.164:8080/SymptomsManagement-0.1.0";
//	public static final String endpoint = "http://192.168.1.4:8080";
	
	@GET("/doctor")
	public List<Doctor> getDoctors();
	
	@POST("/checkin")
	public Integer addCheckin(@Body Checkin checkin);
	
	@GET("/doctor/patientsInfo")
	public List<Checkin> getCheckins(@Query("doctorId") Integer doctorId,
			@Query("ts") Long timestamp);
	
	@POST("/doctor/addPatientMedication")
	public boolean addPatientMedication(@Body PatientMedication patientMedication);
	
	@POST("/doctor/removePatientMedication")
	public boolean removePatientMedication(@Body PatientMedication patientMedication);
	
	@GET("/getAllData")
	public SMData getAllDataFromServer();
	
	@GET("/alerts")
	public List<Alerts> getAlerts(@Query("doctorId") Integer doctorId);
	
	@POST("/acknowledgeAlert")
	public boolean acknowledgeAlert(@Body Integer alertId);
}
