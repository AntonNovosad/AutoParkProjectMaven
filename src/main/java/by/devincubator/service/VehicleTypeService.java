package by.devincubator.service;

import by.devincubator.dto.RentDto;
import by.devincubator.dto.VehicleDto;
import by.devincubator.dto.VehicleTypeDto;
import by.devincubator.entity.Rents;
import by.devincubator.entity.Types;
import by.devincubator.entity.Vehicles;
import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.vehicle.collection.VehicleCollection;
import by.devincubator.vehicle.service.MechanicService;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class VehicleTypeService {
    @Autowired
    private VehicleCollection vehicleCollection;
    @Autowired
    private MechanicService mechanicService;

    public VehicleTypeService() {
    }

    public List<VehicleDto> getVehicles() {
        return vehicleCollection.getListVehicleFromDB().stream()
                .map(vehicle -> {
                    return VehicleDto.builder()
                            .id(vehicle.getId().intValue())
                            .typeId(vehicle.getTypeId().intValue())
                            .typeName(vehicleCollection.getListTypesFromDB().get(vehicle.getTypeId().intValue() - 1).getName())
                            .taxCoefficient(vehicleCollection.getListTypesFromDB().get(vehicle.getTypeId().intValue() - 1).getCoefTaxes())
                            .color(vehicle.getColor())
                            .engineName(vehicle.getEngineName())
                            .engineTaxCoefficient(getEngineTaxCoefficient(vehicle))
                            .tax(getCalcTaxPerMonth(vehicle, vehicleCollection.getListTypesFromDB().get(vehicle.getTypeId().intValue() - 1)))
                            .manufactureYear(vehicle.getManufactureYear())
                            .mileage(vehicle.getMileage())
                            .modelName(vehicle.getModelName())
                            .registrationNumber(vehicle.getRegistrationNumber())
                            .tankVolume(vehicle.getFuelTankCapacity())
                            .weight(vehicle.getWeightKg())
                            .per100km(vehicle.getFuelConsumptionPer100())
                            .maxKm(getMaxKm(vehicle))
                            .income(getTotalIncome())
                            .build();
                }).collect(Collectors.toList());
    }

    public List<VehicleTypeDto> getTypes() {
        return vehicleCollection.getListTypesFromDB().stream()
                .map(type -> {
                    return VehicleTypeDto.builder()
                            .id(type.getId())
                            .name(type.getName())
                            .taxCoefficient(type.getCoefTaxes())
                            .build();
                }).collect(Collectors.toList());
    }

    public List<RentDto> getRent(int id) {
        List<RentDto> rentDtoList = new ArrayList<>();
        vehicleCollection.getListRentsFromDB().forEach(rent -> {
            if (rent.getVehicleId() == id) {
                rentDtoList.add(RentDto.builder()
                        .id(rent.getId())
                        .vehicleId(rent.getVehicleId())
                        .date(rent.getDate())
                        .price(rent.getPrice())
                        .build());
            }
        });
        return rentDtoList;
    }

    public List<VehicleDto> getOrders() {
        return vehicleCollection.getListBrokenVehicle(mechanicService).stream()
                .map(vehicle -> {
                    return VehicleDto.builder()
                            .id(vehicle.getId().intValue())
                            .modelName(vehicle.getModelName())
                            .registrationNumber(vehicle.getRegistrationNumber())
                            .weight(vehicle.getWeightKg())
                            .manufactureYear(vehicle.getManufactureYear())
                            .color(vehicle.getColor())
                            .engineName(vehicle.getEngineName())
                            .mileage(vehicle.getMileage())
                            .tankVolume(vehicle.getFuelTankCapacity())
                            .build();
                }).collect(Collectors.toList());
    }

    private Double getCalcTaxPerMonth(Vehicles vehicles, Types types) {
        return (vehicles.getWeightKg() * 0.0013) + (types.getCoefTaxes() * getEngineTaxCoefficient(vehicles) * 30) + 5;
    }

    private double getTotalIncome() {
        double sum = 0;
        for (Rents r : vehicleCollection.getListRentsFromDB()) {
            sum += r.getPrice();
        }
        return sum;
    }

    private double getEngineTaxCoefficient(Vehicles vehicles) {
        String nameEngine = vehicles.getEngineName();
        double engineTaxCoefficient = 0;
        switch (nameEngine) {
            case "Diesel":
                engineTaxCoefficient = 1.2;
            case "Gasoline":
                engineTaxCoefficient = 1.1;
            case "Electrical":
                engineTaxCoefficient = 0.1;
        }
        return engineTaxCoefficient;
    }

    private double getMaxKm(Vehicles vehicles) {
        return vehicles.getFuelTankCapacity() * 100 / vehicles.getFuelConsumptionPer100();
    }
}
