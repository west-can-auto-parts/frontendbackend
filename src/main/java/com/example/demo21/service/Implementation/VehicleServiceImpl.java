package com.example.demo21.service.Implementation;

import com.example.demo21.dto.ModelResponse;
import com.example.demo21.dto.ProductItem;
import com.example.demo21.dto.VehicleResponse;
import com.example.demo21.repository.ModelRepository;
import com.example.demo21.repository.VehicleRepository;
import com.example.demo21.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Override
    public List<VehicleResponse> getAllVehicle() {
        return vehicleRepository.findAll().stream()
                .map(vehicle -> {
                    VehicleResponse response = new VehicleResponse();
                    response.setId(vehicle.getId());
                    response.setName(vehicle.getName());
                    response.setImageUrl(vehicle.getImageUrl());
                    response.setBannerImageUrl(vehicle.getBannerImageUrl());
                    response.setModel(vehicle.getModel());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VehicleResponse getVehicleByName(String name) {
        return vehicleRepository.findByName(name)
                .map(vehicle -> {
                    VehicleResponse response = new VehicleResponse();
                    response.setId(vehicle.getId());
                    response.setName(vehicle.getName());
                    response.setImageUrl(vehicle.getImageUrl());
                    response.setBannerImageUrl(vehicle.getBannerImageUrl());
                    response.setModel(vehicle.getModel());
                    return response;
                })
                .orElse(null);
    }


    @Override
    public List<ModelResponse> getAllModel() {
        return modelRepository.findAll().stream()
                .map(model -> {
                    ModelResponse response = new ModelResponse();
                    response.setId(model.getId());
                    response.setName(model.getName());
                    response.setImageUrl(model.getImageUrl());

                    // Convert subCategoryAndPosition to expected format
                    Map<String, List<ProductItem>> convertedMap = new HashMap<>();
                    model.getSubCategoryAndProduct().forEach((key, rawList) -> {
                        List<ProductItem> items = rawList.stream().map(raw -> {
                            ProductItem item = new ProductItem();
                            item.setName(raw.getName());
                            item.setImageUrl(raw.getImageUrl());
                            item.setCategoryName(raw.getCategoryName());
                            return item;
                        }).collect(Collectors.toList());
                        convertedMap.put(key, items);
                    });

                    response.setSubCategoryAndProduct(convertedMap);
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ModelResponse getModelByName(String name) {
        return modelRepository.findByName(name)
                .map(model -> {
                    ModelResponse response = new ModelResponse();
                    response.setId(model.getId());
                    response.setName(model.getName());
                    response.setImageUrl(model.getImageUrl());

                    // Convert subCategoryAndPosition to expected format
                    Map<String, List<ProductItem>> convertedMap = new HashMap<>();
                    model.getSubCategoryAndProduct().forEach((key, rawList) -> {
                        List<ProductItem> items = rawList.stream().map(raw -> {
                            ProductItem item = new ProductItem();
                            item.setName(raw.getName());
                            item.setImageUrl(raw.getImageUrl());
                            item.setCategoryName(raw.getCategoryName());
                            return item;
                        }).collect(Collectors.toList());
                        convertedMap.put(key, items);
                    });

                    response.setSubCategoryAndProduct(convertedMap);
                    return response;
                })
                .orElse(null);
    }


}

