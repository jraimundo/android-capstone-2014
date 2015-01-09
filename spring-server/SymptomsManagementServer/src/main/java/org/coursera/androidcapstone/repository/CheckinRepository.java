package org.coursera.androidcapstone.repository;

import java.util.Date;
import java.util.List;

import org.coursera.androidcapstone.entities.Checkin;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckinRepository extends CrudRepository<Checkin, Integer> {

	public List<Checkin> findByCheckinTimestampGreaterThan(Date checkinTimestamp);
	public List<Checkin> findByPatientIdAndCheckinTimestampGreaterThan(Integer patientId, Date checkinTimestamp);
	public List<Checkin> findByPatientIdOrderByCheckinTimestampDesc(Integer patientId);
}
