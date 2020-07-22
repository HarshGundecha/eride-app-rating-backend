package com.happysolutions.erideappratingbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Table
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rideId;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User driver;

    @NotNull
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User passenger;

    @Min(1)
    @Max(5)
    private Integer ratingByPassenger;

    @Min(1)
    @Max(5)
    private Integer ratingByDriver;

}
