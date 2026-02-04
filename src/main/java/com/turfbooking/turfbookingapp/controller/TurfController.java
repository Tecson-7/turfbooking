package com.turfbooking.turfbookingapp.controller;

import com.turfbooking.turfbookingapp.model.Turf;
import com.turfbooking.turfbookingapp.service.TurfService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turfs")
public class TurfController {

    private final TurfService turfService;

    public TurfController(TurfService turfService) {
        this.turfService = turfService;
    }

    @PostMapping
    public Turf addTurf(@RequestBody Turf turf) {
        return turfService.saveTurf(turf);
    }

    @GetMapping
    public List<Turf> getAllTurfs() {
        return turfService.getAllTurfs();
    }
}
