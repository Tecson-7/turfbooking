package com.turfbooking.turfbookingapp.repository;

import com.turfbooking.turfbookingapp.model.Turf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurfRepository extends JpaRepository<Turf, Long> {
}
