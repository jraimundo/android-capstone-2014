package org.coursera.androidcapstone.repository;

import java.util.Collection;

import org.coursera.androidcapstone.entities.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Integer> {

	public Collection<Doctor> findByFirstName(String firstName);
}
