package com.example.demo21.config;

import com.example.demo21.service.ProductService;
import com.example.demo21.service.SuppliersService;
import com.example.demo21.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ApiWarmupScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ApiWarmupScheduler.class);

    @Autowired
    private ProductService productService;

    @Autowired(required = false)
    private SuppliersService suppliersService;

    @Autowired(required = false)
    private VehicleService vehicleService;

    /**
     * This runs every 30 minutes (1800000 milliseconds)
     * It calls frequently used backend methods to keep the app "warm"
     */
    @Scheduled(fixedRate = 1800000)
    public void warmupApis() {
        try {
            logger.info("=== Starting scheduled API warm-up ===");

            // Hit core services to trigger cache population and DB connections
            productService.getAllCategory();
            productService.getAllSubCategory();
            productService.getAllProductCategory();
            productService.getAllBestSellingProduct();
            productService.getShopByCategory();

            // Optional services (if exist)
            if (suppliersService != null) {
                suppliersService.getAll();  // Replace with your real method
            }

            if (vehicleService != null) {
                vehicleService.getAllVehicle();     // Replace with your real method
            }

            logger.info("=== API warm-up completed successfully ===");

        } catch (Exception e) {
            logger.error("Error during scheduled API warm-up", e);
        }
    }
}
