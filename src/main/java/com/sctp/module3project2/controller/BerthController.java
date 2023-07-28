package com.sctp.module3project2.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sctp.module3project2.entity.Berth;
import com.sctp.module3project2.services.BerthService;

import java.util.List;

@RestController
// Responsible of handling HTTP related codes.
/*
 * : http://localhost:8080/api/berth-location
 * : http://localhost:8080/api/berth-location/(id)
 * : http://localhost:8080/api/berth-location/with-availability
 * 
 * GET : /api/berth-location: Get all berth port locations.
 * POST: /api/berth-location: Create a new berth port location.
 * GET : /api/berth-location/{id}: Get a specific berth port location by ID.
 * PUT : /api/berth-location/{id}: Update a specific berth port location.
 * DELETE : /api/berth-location/{id}: Delete a specific berth port location.
 * 
 * POSTMAN: raw JSON
 * {
 * "name": "Berth Name",
 * "location": "Berth Location",
 * "availability": true
 * }
 * 
 * 
 */

@RequestMapping("/api/berth-location")
public class BerthController {
    private final BerthService service;

    public BerthController(BerthService service) {
        this.service = service;
    }

    @GetMapping
    public List<Berth> getAllBerthPortLocations() {
        return service.getAllBerths();
    }

    @PostMapping
    public ResponseEntity<Berth> createBerthPortLocation(@RequestBody Berth berth) {
        Berth createdBerth = service.saveBerth(berth);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBerth);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Berth> getBerthPortLocationById(@PathVariable("id") Long id) {
        Berth berth = service.getBerthById(id);
        if (berth != null) {
            return ResponseEntity.ok(berth);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Berth> updateBerthPortLocation(
            @PathVariable("id") Long id, @RequestBody Berth updatedBerth) {
        Berth berth = service.getBerthById(id);
        if (berth != null) {
            berth.setName(updatedBerth.getName());
            berth.setLocation(updatedBerth.getLocation());
            berth.setAvailability(updatedBerth.isAvailability());
            Berth updatedBerthObj = service.updateBerth(id, berth);
            if (updatedBerthObj != null) {
                return ResponseEntity.ok(updatedBerthObj);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteBerthPortLocation(@PathVariable("id") Long id) {
    //     service.deleteBerth(id);
    //     return ResponseEntity.noContent().build();
    // }

}