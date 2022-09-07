package by.devincubator.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VehicleTypeDto {
    private Long id;
    private String name;
    private double taxCoefficient;
}
