package by.devincubator.parser;

import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.parser.utils.StringUtils;
import by.devincubator.service.TechnicalSpecialist;
import by.devincubator.utils.ReadFile;
import by.devincubator.vehicle.Color;
import by.devincubator.vehicle.Rent;
import by.devincubator.vehicle.Vehicle;
import by.devincubator.vehicle.VehicleType;
import by.devincubator.vehicle.engine.AbstractEngine;
import by.devincubator.vehicle.engine.DieselEngine;
import by.devincubator.vehicle.engine.ElectricalEngine;
import by.devincubator.vehicle.engine.GasolineEngine;

import java.util.ArrayList;
import java.util.List;

public class ParserVehicleFromFile {
    private static final String VEHICLES_PATH = "./src/main/resources/data/vehicles.csv";
    private static final String GASOLINE_ENGINE = "Gasoline";
    private static final String ELECTRICAL_ENGINE = "Electrical";
    @Autowired
    private ParseRentFromFile parseRent;
    @Autowired
    private ParseTypeFromFile parseType;
    @Autowired
    private TechnicalSpecialist technicalSpecialist;
    private List<Vehicle> vehicleList = new ArrayList<>();
    private List<VehicleType> vehicleTypeList = new ArrayList<>();
    private List<Rent> rentList = new ArrayList<>();

    public ParserVehicleFromFile() {
    }

    @InitMethod
    public void init() {
        vehicleTypeList = parseType.loadTypes();
        rentList = parseRent.loadRents();
        vehicleList = loadVehicles();
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public List<VehicleType> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public void setParseRent(ParseRentFromFile parseRent) {
        this.parseRent = parseRent;
    }

    public void setParseType(ParseTypeFromFile parseType) {
        this.parseType = parseType;
    }

    public void setTechnicalSpecialist(TechnicalSpecialist technicalSpecialist) {
        this.technicalSpecialist = technicalSpecialist;
    }

    private List<Vehicle> loadVehicles() {
        List<String> list = ReadFile.readFile(VEHICLES_PATH);
        List<Vehicle> listVehicle = new ArrayList<>();
        for (String s : list) {
            listVehicle.add(createVehicle(s));
        }
        return listVehicle;
    }

    private Vehicle createVehicle(String csvString) {
        Vehicle vehicle = new Vehicle();
        String[] array = StringUtils.createArrayString(csvString);
        vehicle.setId(Integer.parseInt(array[0]));
        vehicle.setType(vehicleTypeList.get(Integer.parseInt(array[1]) - 1));
        vehicle.setModelName(array[2]);
        vehicle.setRegistrationNumber(array[3]);
        vehicle.setWeightKg(Integer.parseInt(array[4]));
        vehicle.setManufactureYear(Integer.parseInt(array[5]));
        vehicle.setMileage(Integer.parseInt(array[6]));
        vehicle.setColor(Color.valueOf(array[7].toUpperCase()));
        vehicle.setEngine(
                createEngine(
                        array[8],
                        Double.parseDouble(array[9]),
                        Double.parseDouble(array[10]),
                        Double.parseDouble(array[array.length - 1])
                )
        );
        vehicle.setRentList(createRentListForVehicleId(rentList, vehicle.getId()));
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

    private List<Rent> createRentListForVehicleId(List<Rent> list, int id) {
        List<Rent> newList = new ArrayList<>();
        for (Rent r : list) {
            if (r.getVehicleId() == id) {
                newList.add(r);
            }
        }
        return newList;
    }
}
