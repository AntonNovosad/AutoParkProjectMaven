package by.devincubator.entity;

import by.devincubator.infrastructure.orm.annotations.Column;
import by.devincubator.infrastructure.orm.annotations.ID;
import by.devincubator.infrastructure.orm.annotations.Table;
import lombok.*;

@Table(name = "vehicles")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehicles {
    @ID
    private Long id;
    @Column(name = "typeId", nullable = false)
    private Long typeId;
    @Column(name = "modelName", nullable = false)
    private String modelName;
    @Column(name = "registrationNumber", nullable = false, unique = true)
    private String registrationNumber;
    @Column(name = "weightKg", nullable = false)
    private Double weightKg;
    @Column(name = "manufactureYear", nullable = false)
    private Integer manufactureYear;
    @Column(name = "mileage")
    private Integer mileage;
    @Column(name = "color")
    private String color;
    @Column(name = "engineName")
    private String engineName;
    @Column(name = "engineCapacity")
    private Double engineCapacity;
    @Column(name = "fuelTankCapacity")
    private Double fuelTankCapacity;
    @Column(name = "fuelConsumptionPer100")
    private Double fuelConsumptionPer100;
}
