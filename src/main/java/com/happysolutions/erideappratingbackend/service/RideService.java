package com.happysolutions.erideappratingbackend.service;

import com.happysolutions.erideappratingbackend.entity.Ride;
import com.happysolutions.erideappratingbackend.entity.User;
import com.happysolutions.erideappratingbackend.enums.UserType;
import com.happysolutions.erideappratingbackend.model.GeneralResponse;
import com.happysolutions.erideappratingbackend.repository.RideRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RideService {

    private final RideRepository rideRepository;
    private final UserService userService;

    @Autowired
    public RideService(RideRepository rideRepository, UserService userService) {
        this.rideRepository = rideRepository;
        this.userService = userService;
    }

    public ResponseEntity rateRide(Long rideId, Integer rating) {
        Ride existingRide = rideRepository.getOne(rideId);
        if (existingRide != null) {
            UserType userType = userService.getUserFromRequest().getUserType();
            switch (userType) {
                case DRIVER:
                    existingRide.setRatingByDriver(rating);
                    existingRide = rideRepository.save(existingRide);
                    break;
                case PASSENGER:
                    existingRide.setRatingByPassenger(rating);
                    existingRide = rideRepository.save(existingRide);
                    break;
            }
            log.info("rate ride called with rideId:{}, rating:{} & response:{}", rideId, rating, existingRide);
            return ResponseEntity.ok().body(existingRide);
        } else {
            log.info("existing ride not found for rideId:{}", rideId);
            return ResponseEntity.badRequest().body(GeneralResponse.builder().status("error").message("Invalid request").build());
        }
    }

    public ResponseEntity getAverageRating() {
        User user = userService.getUserFromRequest();
        UserType userType = user.getUserType();
        Float averageRating = 0F;
        switch (userType) {
            case DRIVER:
                averageRating = rideRepository.findAverageDriverRating(user);
                break;
            case PASSENGER:
                averageRating = rideRepository.findAveragePassengerRating(user);
                break;
        }
        if (averageRating == null)
            averageRating = 0F;
        Map response = Map.of("userId", user.getUserId(), "name", user.getName(), "userType", userType, "averageRating", averageRating);
        log.info("average rating fetched for userId:{}, response:{}", user.getUserId(), response);
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity getNewRide() {
        User user = userService.getUserFromRequest();
        if (userService.isPassenger(user)) {
            return ResponseEntity.ok(rideRepository.save(Ride.builder().passenger(user).build()));
        } else {
            return ResponseEntity.badRequest().body(GeneralResponse.builder().status("error").message("Invalid request").build());
        }
    }

    public ResponseEntity getAvailableRide() {
        User user = userService.getUserFromRequest();
        if (userService.isDriver(user)) {
            List<Ride> rides = rideRepository.findAllByDriverIsNull();
            log.info("fetched available rides for driver:{}, rides:{}", user.getUserId(), rides);
            return ResponseEntity.ok(rides);
        } else {
            log.info("invalid get available rides request made by user:{}", user);
            return ResponseEntity.badRequest().body(GeneralResponse.builder().status("error").message("Invalid request").build());
        }
    }

    public ResponseEntity acceptRide(Long rideId) {
        Ride ride = rideRepository.getOne(rideId);
        User user = userService.getUserFromRequest();
        if (userService.isDriver(user) && ride != null) {
            ride.setDriver(user);
            log.info("accepted ride:{}, userId:{}", ride, user.getUserId());
            return ResponseEntity.ok(rideRepository.save(ride));
        }
        log.info("invalid get accept ride request made by user:{}", user);
        return ResponseEntity.badRequest().body(GeneralResponse.builder().status("error").message("Invalid request").build());
    }

}
