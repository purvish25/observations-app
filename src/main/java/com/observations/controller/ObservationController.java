package com.observations.controller;

import com.observations.model.Observation;
import com.observations.repository.ObservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/observations")
public class ObservationController {

    @Autowired
    private ObservationRepository observationRepository;

    @GetMapping
    public List<Observation> getAllObservations() {
        return observationRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createObservation(@RequestBody Observation observation) {
        if (observation != null) {
            observationRepository.save(observation);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateObservation(@PathVariable Long id, @RequestBody Observation observation) {
        Optional<Observation> optionalObservation = observationRepository.findById(id);

        if (optionalObservation.isPresent()) {
            Observation existingObservation = optionalObservation.get();
            existingObservation.setType(observation.getType());
            existingObservation.setDate(observation.getDate());
            existingObservation.setPatient(observation.getPatient());
            existingObservation.setValue(observation.getValue());
            existingObservation.setUnit(observation.getUnit());
            observationRepository.save(existingObservation);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Handle the not-found case appropriately
        }
    }

    @DeleteMapping("/{id}")
    public void deleteObservation(@PathVariable Long id) {
        observationRepository.deleteById(id);
    }
}