package com.ntm.hospital.service;

import com.ntm.hospital.data.Physician;
import com.ntm.hospital.exception.PhysicianNotFoundException;
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

    public Physician findPhysician(Integer entityId) {

        Optional<Physician> optionalPhysician = physicianRepository.findById(entityId);

        if (optionalPhysician.isPresent()) {
            return optionalPhysician.get();
        } else {
            throw new PhysicianNotFoundException("Physician Not Found");
        }
    }

    public List<Physician> listPhysicians() {
        return StreamSupport.stream(
                physicianRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Physician addPhysician(Physician physician) {
        return physicianRepository.save(physician);
    }

    public Physician changePhysician(Integer entityId, Physician physician) {
        Optional<Physician> optionalPhysician = physicianRepository.findById(entityId);

        if (optionalPhysician.isPresent()) {
            Physician physicianToChange = optionalPhysician.get();
            physicianToChange.setName(physician.getName());
            physicianToChange.setPosition(physician.getPosition());
            physicianToChange.setSsn(physician.getSsn());
            return physicianRepository.save(physicianToChange);
        } else {
            throw new PhysicianNotFoundException("Physician Not Found");
        }
    }

    public void removePhysician(Physician physician) {
        physicianRepository.delete(physician);
    }
}
