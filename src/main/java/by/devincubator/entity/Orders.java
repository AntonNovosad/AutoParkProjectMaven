package by.devincubator.entity;

import by.devincubator.infrastructure.orm.annotations.Column;
import by.devincubator.infrastructure.orm.annotations.ID;
import by.devincubator.infrastructure.orm.annotations.Table;
import lombok.*;

@Table(name = "orders")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @ID
    private Long id;

    @Column(name = "vehicle_id")
    private Long vehiclesId;

    @Column(name = "defect")
    private String defect;

    @Column(name = "breakingAmount")
    private String breakingAmount;
}
