package com.ntm.hospital.repository;

import com.ntm.hospital.data.Physician;
import org.springframework.data.repository.CrudRepository;

public interface PhysicianRepository extends CrudRepository<Physician, Long> {
}
