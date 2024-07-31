package com.example.intellipark.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.example.intellipark.models.Vehicle;
import com.example.intellipark.repositories.VehicleRepository;

@Service
public class VehicleService {

  @Autowired
  private VehicleRepository vehicleRepository;

  private final String API_BASE_URL = "https://api.gov.example.com/vehicles/DecodeVinValues/";

  public Vehicle findByRegistrationNumber(String registrationNumber) {
    // This can be used to fetch data from your database if available
    return vehicleRepository.findByRegistrationNumber(registrationNumber);
  }

  public void save(Vehicle vehicle) {
    vehicleRepository.save(vehicle);
  }

  public String getVehicleDetailsFromAPI(String registrationNumber) {
    RestTemplate restTemplate = new RestTemplate();
    String url = UriComponentsBuilder.fromHttpUrl(API_BASE_URL + registrationNumber)
            .queryParam("format", "json")
            .build()
            .toString();
    return restTemplate.getForObject(url, String.class);
  }
}
