package com.example.demo21.controller;

import com.example.demo21.dto.ModelResponse;
import com.example.demo21.dto.VehicleResponse;
import com.example.demo21.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // GET /api/vehicles
    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleResponse>> getAllVehicles() {
        List<VehicleResponse> vehicles = vehicleService.getAllVehicle();
        return ResponseEntity.ok(vehicles);
    }

    // GET /api/vehicles/{id}
    @GetMapping("/vehicle/{name}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable String name) {
        name=slugToOriginalName(name);
        VehicleResponse vehicle = vehicleService.getVehicleByName(name);
        if (vehicle != null) {
            return ResponseEntity.ok(vehicle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/models
    @GetMapping("/models")
    public ResponseEntity<List<ModelResponse>> getAllModels() {
        List<ModelResponse> models = vehicleService.getAllModel();
        return ResponseEntity.ok(models);
    }

    // GET /api/models/{id}
    @GetMapping("/model/{name}")
    public ResponseEntity<ModelResponse> getModelById(@PathVariable String name) {
        String vehicleName=getVehicleName(name);
        name=removeVehicleName(name);
        if(vehicleName.equals("jaguar")|| vehicleName.equals("mazda")|| vehicleName.equals("honda")){
            name=slugToEspecialName(name,vehicleName);
        }
        else {
            name = slugToOriginalName(name);
        }
        ModelResponse model = vehicleService.getModelByName(name);
        if (model != null) {
            return ResponseEntity.ok(model);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    private String slugToOriginalName(String slug) {
        if (slug == null || slug.isEmpty()) {
            return slug;
        }
        // Replace hyphens with spaces, underscores with slashes, and convert the string to uppercase
        return slug.replace("-", " ").replace("%2B","-").replace("_", "/").toUpperCase();
    }
    private String slugToEspecialName(String slug, String vehicleName) {
        if (slug == null || slug.isEmpty()) {
            return slug;
        }

        // Decode and normalize
        slug = slug.replace("%2B", "-").replace("_", "/");

        // Only apply the rule for Mazda and Honda
        boolean isMazda = vehicleName.equalsIgnoreCase("mazda") && !slug.contains("x-");
        boolean isHonda = vehicleName.equalsIgnoreCase("honda") && !slug.contains("r-");

        if (isMazda || isHonda) {
            // Remove only the second hyphen
            int first = slug.indexOf("-");
            int second = slug.indexOf("-", first + 1);
            if (second != -1) {
                slug = slug.substring(0, second) + slug.substring(second + 1);
            }

            // Convert to uppercase and replace remaining hyphens with spaces
            return slug.replace("-", " ").toUpperCase();
        }

        // Default case
        return slug.toUpperCase();
    }


    private String removeVehicleName(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        if (str.contains("-")) {
            String[] parts = str.split("-");
            // Join all parts except the first one
            return String.join("-", java.util.Arrays.copyOfRange(parts, 1, parts.length));
        }
        return str;
    }

    public static String getVehicleName(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        // Split by hyphen
        String[] hyphenParts = str.split("-");
        // Get the first part
        String firstPart = hyphenParts[0];
        // Split by whitespace
        String[] words = firstPart.trim().split("\\s+");
        // Return the first word if it exists
        return words.length > 0 ? words[0] : "";
    }

}

