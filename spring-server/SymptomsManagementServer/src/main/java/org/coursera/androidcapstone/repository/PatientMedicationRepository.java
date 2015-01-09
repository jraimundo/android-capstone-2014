package org.coursera.androidcapstone.repository;

import org.coursera.androidcapstone.entities.PatientMedication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientMedicationRepository extends CrudRepository<PatientMedication, Integer> {

	public PatientMedication findByPatientIdAndMedicationId(Integer patientId, Integer MedicationId);
}
