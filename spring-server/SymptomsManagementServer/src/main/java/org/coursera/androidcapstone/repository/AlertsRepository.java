package org.coursera.androidcapstone.repository;

import java.util.Collection;

import org.coursera.androidcapstone.entities.Alerts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertsRepository extends CrudRepository<Alerts, Integer> {

	public Alerts findByAlertId(Integer alertId);
	public Collection<Alerts> findByPatientId(Integer patientId);
}
