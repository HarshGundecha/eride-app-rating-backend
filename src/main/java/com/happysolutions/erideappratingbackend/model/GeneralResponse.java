package com.happysolutions.erideappratingbackend.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneralResponse {
    private String status;
    private String message;
}
