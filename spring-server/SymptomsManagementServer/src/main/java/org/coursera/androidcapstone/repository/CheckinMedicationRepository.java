package org.coursera.androidcapstone.repository;

import java.util.List;

import org.coursera.androidcapstone.entities.CheckinMedication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckinMedicationRepository extends CrudRepository<CheckinMedication, Integer> {

	public List<CheckinMedication> findByCheckinId(Integer checkinId);
}
