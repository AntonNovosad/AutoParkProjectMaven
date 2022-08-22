package by.devincubator.parser;

import by.devincubator.entity.Types;
import by.devincubator.entity.Vehicles;
import by.devincubator.vehicle.service.TechnicalSpecialist;

import java.util.List;

public interface ParserVehicleFromFile {
    List<Vehicles> getVehicleList();
    List<Types> getVehicleTypeList();
    void setParseRent(ParserRentFromFile parseRent);
    void setTechnicalSpecialist(TechnicalSpecialist technicalSpecialist);
}
