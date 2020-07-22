package com.happysolutions.erideappratingbackend.controller;

import com.happysolutions.erideappratingbackend.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/ride")
public class RideController {

    private final RideService rideService;

    @Autowired
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PutMapping("/rating/{rideId}/{rating}")
    public ResponseEntity rateRide(@NotNull @PathVariable(name = "rideId") Long rideId, @NotNull @PathVariable(name = "rating") Integer rating) {
        return rideService.rateRide(rideId, rating);
    }

    @GetMapping
    public ResponseEntity getAvailableRide() {
        return rideService.getAvailableRide();
    }

    @PutMapping("accept/{rideId}")
    public ResponseEntity acceptRide(@NotNull @PathVariable Long rideId) {
        return rideService.acceptRide(rideId);
    }

    @PostMapping
    public ResponseEntity getNewRide() {
        return rideService.getNewRide();
    }

    @GetMapping("/rating")
    public ResponseEntity getAverageRating() {
        return rideService.getAverageRating();
    }

}
