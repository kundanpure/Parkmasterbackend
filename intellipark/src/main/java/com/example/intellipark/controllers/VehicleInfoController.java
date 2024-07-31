package com.example.intellipark.controllers;

import com.example.intellipark.services.VehicleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleInfoController {

    @Autowired
    private VehicleInfoService vehicleInfoService;

    @GetMapping("/details")
    public Mono<ResponseEntity<String>> getVehicleDetails(@RequestParam String registrationNumber, @RequestParam int modelYear) {
        return vehicleInfoService.getVehicleDetailsFromAPI(registrationNumber, modelYear)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
