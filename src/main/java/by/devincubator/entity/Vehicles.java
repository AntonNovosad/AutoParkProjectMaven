package by.devincubator.entity;

import by.devincubator.infrastructure.orm.annotations.Column;
import by.devincubator.infrastructure.orm.annotations.ID;
import by.devincubator.infrastructure.orm.annotations.Table;
import by.devincubator.vehicle.engine.Startable;
import lombok.*;

import java.util.List;

@Table(name = "vehicles")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehicles {

    @ID
    private Long id;

    @Column(name = "type_id", nullable = false)
    private Long typeId;

    @Column(name = "model_name", nullable = false)
    private String modelName;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @Column(name = "weight", nullable = false)
    private Double weightKg;

    @Column(name = "manufacture_year", nullable = false)
    private Integer manufactureYear;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "color")
    private String color;

    private Startable engine;

    private List<Rents> rentList;
}
