package com.example.musicplate.repositories;

import com.example.musicplate.models.Plate;

import java.util.List;

public interface PlateRepo {
    void removePlate(Long id);
    void updatePlate(Long id, Plate plate);
    List<Plate> getAllPlates();
    void addPlate(Plate plate);
    Plate getPlate(Long Id);
}
