package com.ntm.hospital.service;

import com.ntm.hospital.data.Patient;
import com.ntm.hospital.repository.PatientRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Optional<Patient> findById(Long entityId) {
        return patientRepository.findById(entityId);
    }

    public List<Patient> findAll() {
        return StreamSupport.stream(
                patientRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient update(Patient patient) {
        return create(patient);
    }

    public void delete(Patient patient) {
        patientRepository.delete(patient);
    }
}
