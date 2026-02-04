package com.turfbooking.turfbookingapp.service;

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

    public Turf saveTurf(Turf turf) {
        return turfRepository.save(turf);
    }

    public List<Turf> getAllTurfs() {
        return turfRepository.findAll();
    }
}
