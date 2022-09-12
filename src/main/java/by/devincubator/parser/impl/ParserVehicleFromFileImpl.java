package by.devincubator.parser.impl;

import by.devincubator.entity.Rents;
import by.devincubator.entity.Types;
import by.devincubator.entity.Vehicles;
import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.parser.ParserRentFromFile;
import by.devincubator.parser.ParserTypeFromFile;
import by.devincubator.parser.ParserVehicleFromFile;
import by.devincubator.parser.utils.StringUtils;
import by.devincubator.utils.ReadFile;
import by.devincubator.vehicle.engine.AbstractEngine;
import by.devincubator.vehicle.engine.DieselEngine;
import by.devincubator.vehicle.engine.ElectricalEngine;
import by.devincubator.vehicle.engine.GasolineEngine;
import by.devincubator.vehicle.service.TechnicalSpecialist;

import java.util.ArrayList;
import java.util.List;

public class ParserVehicleFromFileImpl implements ParserVehicleFromFile {
    private static final String VEHICLES_PATH = "./src/main/resources/data/vehicles.csv";
    private static final String GASOLINE_ENGINE = "Gasoline";
    private static final String ELECTRICAL_ENGINE = "Electrical";
    @Autowired
    private ParserRentFromFile parseRent;
    @Autowired
    private ParserTypeFromFile parseType;
    @Autowired
    private TechnicalSpecialist technicalSpecialist;
    private List<Vehicles> vehicleList = new ArrayList<>();
    private List<Types> vehicleTypeList = new ArrayList<>();
    private List<Rents> rentList = new ArrayList<>();

    public ParserVehicleFromFileImpl() {
    }

    public void init() {
        vehicleTypeList = parseType.loadTypes();
        rentList = parseRent.loadRents();
        vehicleList = loadVehicles();
    }

    public List<Vehicles> getVehicleList() {
        return vehicleList;
    }

    public List<Types> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public void setParseRent(ParserRentFromFile parseRent) {
        this.parseRent = parseRent;
    }

    public void setParseType(ParserTypeFromFile parseType) {
        this.parseType = parseType;
    }

    public void setTechnicalSpecialist(TechnicalSpecialist technicalSpecialist) {
        this.technicalSpecialist = technicalSpecialist;
    }

    public List<Vehicles> loadVehicles() {
        List<String> list = ReadFile.readFile(VEHICLES_PATH);
        List<Vehicles> listVehicle = new ArrayList<>();
        for (String s : list) {
            listVehicle.add(createVehicle(s));
        }
        return listVehicle;
    }

    private Vehicles createVehicle(String csvString) {
        Vehicles vehicle = new Vehicles();
        String[] array = StringUtils.createArrayString(csvString);
        vehicle.setId(Long.parseLong(array[0]));
        vehicle.setTypeId(vehicleTypeList.get(Integer.parseInt(array[1]) - 1).getId());
        vehicle.setModelName("'" + array[2] + "'");
        vehicle.setRegistrationNumber("'" + array[3] + "'");
        vehicle.setWeightKg(Double.parseDouble(array[4]));
        vehicle.setManufactureYear(Integer.parseInt(array[5]));
        vehicle.setMileage(Integer.parseInt(array[6]));
        vehicle.setColor("'" + array[7].toUpperCase() + "'");
        vehicle.setEngineName("'" + array[8] + "'");
        vehicle.setEngineCapacity(Double.parseDouble(array[9]));
        vehicle.setFuelTankCapacity(Double.parseDouble(array[10]));
        vehicle.setFuelConsumptionPer100(Double.parseDouble(array[array.length - 1]));
        return vehicle;
    }

    private AbstractEngine createEngine(String name, double engineCapacity, double fuel100, double fuelTank) {
        if (name.equals(GASOLINE_ENGINE)) {
            return new GasolineEngine(engineCapacity, fuelTank, fuel100);
        } else if (name.equals(ELECTRICAL_ENGINE)) {
            return new ElectricalEngine(engineCapacity, fuel100);
        } else {
            return new DieselEngine(engineCapacity, fuelTank, fuel100);
        }
    }

    private List<Rents> createRentListForVehicleId(List<Rents> list, Long id) {
        List<Rents> newList = new ArrayList<>();
        for (Rents r : list) {
            if (r.getVehicleId() == id) {
                newList.add(r);
            }
        }
        return newList;
    }
}
