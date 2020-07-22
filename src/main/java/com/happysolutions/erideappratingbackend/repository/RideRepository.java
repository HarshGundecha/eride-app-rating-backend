package com.happysolutions.erideappratingbackend.repository;

import com.happysolutions.erideappratingbackend.entity.Ride;
import com.happysolutions.erideappratingbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    @Query("SELECT AVG(ratingByDriver) from Ride where passenger = ?1 ")
    public Float findAveragePassengerRating(User user);

    @Query("SELECT AVG(ratingByPassenger) from Ride where driver = ?1 ")
    public Float findAverageDriverRating(User user);

    public List<Ride> findAllByDriverIsNull();

}