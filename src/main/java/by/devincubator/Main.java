package by.devincubator;

import by.devincubator.exception.DefectedVehicleException;
import by.devincubator.service.MechanicService;
import by.devincubator.service.RentVehicle;
import by.devincubator.vehicle.*;
import by.devincubator.vehicle.collection.VehicleCollection;

import java.util.Comparator;
import java.util.List;

public class Main {
    private static final String PATH = "./src/main/resources/data/";
    private static final String TYPES_PATH = PATH + "types.csv";
    private static final String VEHICLES_PATH = PATH + "vehicles.csv";
    private static final String RENTS_PATH = PATH + "rents.csv";

    public static void main(String[] args) {
        VehicleCollection collection = new VehicleCollection(TYPES_PATH, VEHICLES_PATH, RENTS_PATH);
        MechanicService mechanicService = new MechanicService();

        List<Vehicle> brokenVehicleList = collection.getListBrokenVehicle(mechanicService, VEHICLES_PATH);
        displayVehicleList(brokenVehicleList);

        List<Vehicle> sortBrokenVehicleList = collection.getListCountBrokenVehicle(brokenVehicleList);
        displayVehicleList(sortBrokenVehicleList);

        System.out.println("Vehicle with max tax: " + collection.getVehicleWithMaxTas(VEHICLES_PATH));

        findAndDisplayVolkswagenVehicle(brokenVehicleList);

        findAndDisplayNewVehicle(brokenVehicleList);

        washVehicle(collection.loadVehicles(VEHICLES_PATH));
        garageVehicle(collection.loadVehicles(VEHICLES_PATH));

        addRent(collection, mechanicService);

        mechanicService.showVehicleWithoutBrokenDetails(collection.getVehicleList());

        mechanicService.showVehicleWithMaxBrokenDetails(brokenVehicleList);

        brokenVehicleList.forEach(vehicle -> mechanicService.repair(vehicle));
    }

    private static void addRent(VehicleCollection collection, MechanicService mechanicService) {
        RentVehicle rentVehicle = new RentVehicle();
        for (Vehicle v : collection.getVehicleList()) {
            try {
                rentVehicle.rentVehicle(v, mechanicService, 100);
            } catch (DefectedVehicleException e) {
                e.getMessage();
            }
        }
    }

    private static void findAndDisplayVolkswagenVehicle(List<Vehicle> vehicleList) {
        vehicleList
                .stream()
                .filter(vehicle -> vehicle.getModelName().matches(".*Volkswagen.*"))
                .forEach(System.out::println);
    }

    private static void findAndDisplayNewVehicle(List<Vehicle> vehicleList) {
        System.out.println("The newest vehicle: " + vehicleList
                .stream()
                .filter(vehicle -> vehicle.getModelName().matches(".*Volkswagen.*"))
                .max(Comparator.comparingInt(Vehicle::getManufactureYear))
                .get());
    }

    private static void displayVehicleList(List<Vehicle> vehicleList) {
        vehicleList.forEach(System.out::println);
    }

    private static void washVehicle(List<Vehicle> vehicleList) {
        vehicleList
                .stream()
                .forEach(vehicle -> System.out.println("Auto " + vehicle.getId() + " washed"));
    }

    private static void garageVehicle(List<Vehicle> vehicleList) {
        vehicleList
                .stream()
                .forEach(vehicle -> System.out.println("Auto " + vehicle.getId() + " entered in garage"));
        vehicleList
                .stream()
                .sorted((o1, o2) -> o2.getId() - o1.getId())
                .forEach(vehicle -> System.out.println("Auto " + vehicle.getId() + " went out from garage"));
    }
}