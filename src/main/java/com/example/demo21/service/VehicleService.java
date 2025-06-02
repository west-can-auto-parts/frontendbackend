package com.example.demo21.service;

import com.example.demo21.dto.ModelResponse;
import com.example.demo21.dto.VehicleResponse;

import java.util.List;

public interface VehicleService {
    List<VehicleResponse> getAllVehicle();
    VehicleResponse getVehicleByName(String name);
    List<ModelResponse> getAllModel();
    ModelResponse getModelByName(String id);
}
