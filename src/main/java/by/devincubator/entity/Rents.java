package by.devincubator.entity;

import by.devincubator.infrastructure.orm.annotations.Column;
import by.devincubator.infrastructure.orm.annotations.ID;
import by.devincubator.infrastructure.orm.annotations.Table;
import lombok.*;

@Table(name = "rents")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rents {
    @ID
    private Long id;
    @Column(name = "vehicleId")
    private Long vehicleId;
    @Column(name = "date")
    private String date;
    @Column(name = "price")
    private Double price;
}
