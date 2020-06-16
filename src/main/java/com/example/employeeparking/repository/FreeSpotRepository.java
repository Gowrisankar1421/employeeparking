package com.example.employeeparking.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeeparking.model.FreeSpot;

public interface FreeSpotRepository extends JpaRepository<FreeSpot, Long> {

	List<FreeSpot> findByDateGreaterThanAndStatus(LocalDate date, String string);

}
