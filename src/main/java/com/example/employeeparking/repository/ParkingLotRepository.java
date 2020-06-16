package com.example.employeeparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeeparking.model.ParkingLot;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

}
