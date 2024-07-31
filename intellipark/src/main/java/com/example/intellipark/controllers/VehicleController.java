package com.example.intellipark.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.intellipark.services.VehicleInfoService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    private VehicleInfoService vehicleInfoService;

    @GetMapping("/search/{registrationNumber}")
    public Mono<String> searchVehicleDetails(@PathVariable String registrationNumber, @RequestParam int modelYear) {
        return vehicleInfoService.getVehicleDetailsFromAPI(registrationNumber, modelYear);
    }
}
