package by.devincubator.parser.impl;

import by.devincubator.entity.Rents;
import by.devincubator.entity.Types;
import by.devincubator.entity.Vehicles;
import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.parser.ParserVehicleInterface;
import by.devincubator.service.RentsService;
import by.devincubator.service.TypesService;
import by.devincubator.service.VehiclesService;
import lombok.Setter;

import java.util.List;

@Setter
public class ParserVehicleFromDB implements ParserVehicleInterface {
    @Autowired
    private TypesService typesService;
    @Autowired
    private VehiclesService vehiclesService;
    @Autowired
    private RentsService rentsService;

    public List<Types> loadTypes() {
        return typesService.getAll();
    }

    public List<Rents> loadRents() {
        return rentsService.getAll();
    }

    public List<Vehicles> loadVehicles() {
        return vehiclesService.getAll();
    }
}
