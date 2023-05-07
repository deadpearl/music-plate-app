package com.example.musicplate.service;

import com.example.musicplate.models.Plate;
import com.example.musicplate.repositories.PlateRepo;

import java.util.List;

public class  PlateService {

    private final PlateRepo plateRepo;

    public PlateService(PlateRepo plateRepo) {
        this.plateRepo = plateRepo;
    }

    public void removePlate(Long id) {
        plateRepo.removePlate(id);
    }

    public void updatePlate(Long id, Plate plate) {
        plateRepo.updatePlate(id, plate);
    }

    public void addPlate(Plate plate) {
        plateRepo.addPlate(plate);
    }

    public Plate getPlate(Long id) {
        return plateRepo.getPlate(id);
    }
    public List<Plate> getAllPlates() {
        return plateRepo.getAllPlates();
    }
}

