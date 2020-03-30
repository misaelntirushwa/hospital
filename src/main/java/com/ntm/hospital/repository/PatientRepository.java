package com.ntm.hospital.repository;

import com.ntm.hospital.data.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Long> {
}
