package com.example.intellipark.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class VehicleInfoService {
    private static final Logger logger = LoggerFactory.getLogger(VehicleInfoService.class);
    private final WebClient webClient;

    public VehicleInfoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://vpic.nhtsa.dot.gov/api").build();
    }

    public Mono<String> getVehicleDetailsFromAPI(String registrationNumber, int modelYear) {
        logger.info("Fetching details for registration number: {} and model year: {}", registrationNumber, modelYear);

        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/vehicles/DecodeVinValuesExtended/{registrationNumber}")
                        .queryParam("format", "json")
                        .queryParam("modelyear", modelYear)
                        .build(registrationNumber))
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> logger.info("Received response: {}", response))
                .doOnError(e -> logger.error("Error fetching vehicle details", e));
    }
}
