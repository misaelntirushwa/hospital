package com.ntm.hospital.service;

import com.ntm.hospital.data.Physician;
import com.ntm.hospital.repository.PhysicianRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PhysicianService {

    private final PhysicianRepository physicianRepository;

    public PhysicianService(PhysicianRepository physicianRepository) {
        this.physicianRepository = physicianRepository;
    }

    public Optional<Physician> findById(Long entityId) {
        return physicianRepository.findById(entityId);
    }

    public List<Physician> findAll() {
        return StreamSupport.stream(
                physicianRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Physician create(Physician physician) {
        return physicianRepository.save(physician);
    }

    public Physician update(Physician physician) {
        return create(physician);
    }

    public void delete(Physician physician) {
        physicianRepository.delete(physician);
    }
}
