package com.turfbooking.turfbookingapp.service;

import com.turfbooking.turfbookingapp.exception.BadRequestException;
import com.turfbooking.turfbookingapp.exception.TurfNotFoundException;
import com.turfbooking.turfbookingapp.model.Turf;
import com.turfbooking.turfbookingapp.repository.TurfRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurfService {

    private final TurfRepository turfRepository;

    public TurfService(TurfRepository turfRepository) {
        this.turfRepository = turfRepository;
    }

    // Create a new turf
    public Turf createTurf(Turf turf) {
        if (turf.getName() == null || turf.getLocation() == null || turf.getPricePerHour() <= 0) {
            throw new BadRequestException("Turf name, location, and price per hour are required");
        }
        turf.setAvailable(true); // Default available
        return turfRepository.save(turf);
    }

    // Get all turfs
    public List<Turf> getAllTurfs() {
        return turfRepository.findAll();
    }

    // Get single turf by id
    public Turf getTurfById(Long id) {
        return turfRepository.findById(id)
                .orElseThrow(() -> new TurfNotFoundException("Turf not found with id: " + id));
    }

    // Update a turf
    public Turf updateTurf(Long id, Turf updatedTurf) {
        Turf existing = getTurfById(id);

        if (updatedTurf.getName() != null) existing.setName(updatedTurf.getName());
        if (updatedTurf.getLocation() != null) existing.setLocation(updatedTurf.getLocation());
        if (updatedTurf.getPricePerHour() > 0) existing.setPricePerHour(updatedTurf.getPricePerHour());
        existing.setAvailable(updatedTurf.isAvailable());

        return turfRepository.save(existing);
    }

    // Delete a turf
    public void deleteTurf(Long id) {
        Turf existing = getTurfById(id);
        turfRepository.delete(existing);
    }
}
