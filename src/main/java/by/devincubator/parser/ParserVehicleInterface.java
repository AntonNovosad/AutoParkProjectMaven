package by.devincubator.parser;

import by.devincubator.entity.Rents;
import by.devincubator.entity.Types;
import by.devincubator.entity.Vehicles;

import java.util.List;

public interface ParserVehicleInterface {
    List<Types> loadTypes();
    List<Rents> loadRents();
    List<Vehicles> loadVehicles();
}
