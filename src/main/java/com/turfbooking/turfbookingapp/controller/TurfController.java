package com.turfbooking.turfbookingapp.controller;

import com.turfbooking.turfbookingapp.model.Turf;
import com.turfbooking.turfbookingapp.service.TurfService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turfs")
@CrossOrigin
public class TurfController {

    private final TurfService turfService;

    public TurfController(TurfService turfService) {
        this.turfService = turfService;
    }

    // Create a new turf
    @PostMapping
    public Turf createTurf(@RequestBody Turf turf) {
        return turfService.createTurf(turf);
    }

    // Get all turfs
    @GetMapping
    public List<Turf> getAllTurfs() {
        return turfService.getAllTurfs();
    }

    // Get single turf
    @GetMapping("/{id}")
    public Turf getTurfById(@PathVariable Long id) {
        return turfService.getTurfById(id);
    }

    // Update a turf
    @PutMapping("/{id}")
    public Turf updateTurf(@PathVariable Long id, @RequestBody Turf turf) {
        return turfService.updateTurf(id, turf);
    }

    // Delete a turf
    @DeleteMapping("/{id}")
    public void deleteTurf(@PathVariable Long id) {
        turfService.deleteTurf(id);
    }
}
