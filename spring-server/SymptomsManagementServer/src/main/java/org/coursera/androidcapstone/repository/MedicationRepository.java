package org.coursera.androidcapstone.repository;

import org.coursera.androidcapstone.entities.Medication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends CrudRepository<Medication, Integer> {

}
