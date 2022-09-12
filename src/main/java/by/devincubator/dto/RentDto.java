package by.devincubator.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RentDto {
    private Long id;
    private Long vehicleId;
    private String date;
    private double price;
}
