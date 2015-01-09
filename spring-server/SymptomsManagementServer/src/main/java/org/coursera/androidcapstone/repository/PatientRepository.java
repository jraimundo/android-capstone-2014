package org.coursera.androidcapstone.repository;

import java.util.List;

import org.coursera.androidcapstone.entities.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {

	public List<Patient> findByDoctorId(Integer doctorId);
//	public List<Integer> findPatientIdByDoctorId(Integer doctorId);
}
