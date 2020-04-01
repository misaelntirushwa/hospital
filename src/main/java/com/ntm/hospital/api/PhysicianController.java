package com.ntm.hospital.api;

import com.ntm.hospital.data.Physician;
import com.ntm.hospital.exception.PhysicianNotFoundException;
import com.ntm.hospital.service.PhysicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/physicians")
public class PhysicianController {

    private final PhysicianService physicianService;

    @Autowired
    public PhysicianController(PhysicianService physicianService) {
        this.physicianService = physicianService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Physician>> getAllPhysicians() {
        List<Physician> physicianList = physicianService.listPhysicians();
        return new ResponseEntity<List<Physician>>(physicianList, HttpStatus.OK);
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Physician> getPhysician(@PathVariable("id") Integer entityId) {
        try {
            Physician physician = physicianService.findPhysician(entityId);

            return new ResponseEntity<Physician>(physician, HttpStatus.OK);
        } catch (PhysicianNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Physician Not Found");
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Physician> addPhysician(@Valid @RequestBody Physician physician) {
        Physician addedPhysician = physicianService.addPhysician(physician);

        return new ResponseEntity<Physician>(addedPhysician, HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Physician> changePhysician(@PathVariable("id") Integer entityId,
                                                     @Valid @RequestBody Physician physician) {
        try {
            Physician changedPhysician = physicianService.changePhysician(entityId, physician);

            return new ResponseEntity<Physician>(changedPhysician, HttpStatus.OK);
        } catch (PhysicianNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Physician Not Found");
        }
    }

}
