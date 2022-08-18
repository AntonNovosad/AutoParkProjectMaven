package by.devincubator.vehicle.collection;

import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.parser.ParserVehicleFromFile;
import by.devincubator.service.MechanicService;
import by.devincubator.vehicle.Column;
import by.devincubator.vehicle.Vehicle;
import by.devincubator.vehicle.VehicleType;
import by.devincubator.vehicle.comparator.ComparatorByDefectCount;
import by.devincubator.vehicle.comparator.ComparatorByTaxPerMonth;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VehicleCollection {
    private List<VehicleType> vehicleTypes = new ArrayList<>();
    private List<Vehicle> vehicleList = new ArrayList<>();
    @Autowired
    private ParserVehicleFromFile parser;

    public VehicleCollection() {
    }

    @InitMethod
    public void init() {
        vehicleTypes = parser.getVehicleTypeList();
        vehicleList = parser.getVehicleList();
    }

    public List<VehicleType> getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(List<VehicleType> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public ParserVehicleFromFile getParser() {
        return parser;
    }

    public void setParser(ParserVehicleFromFile parser) {
        this.parser = parser;
    }

    public List<Vehicle> getListBrokenVehicle(MechanicService mechanicService) {
        return vehicleList
                .stream()
                .filter(x -> !mechanicService.detectBreaking(x).isEmpty())
                .collect(Collectors.toList());
    }

    public List<Vehicle> getListCountBrokenVehicle(List<Vehicle> vehicleList) {
        return vehicleList
                .stream()
                .sorted(new ComparatorByDefectCount())
                .collect(Collectors.toList());
    }

    public Optional<Vehicle> getVehicleWithMaxTas() {
        return Optional.of(vehicleList
                .stream()
                .max(new ComparatorByTaxPerMonth())
                .get());
    }

    public void insert(int index, Vehicle v) {
        if (isIndex(index)) {
            vehicleList.add(index, v);
        } else {
            vehicleList.add(v);
        }
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public int delete(int index) {
        if (isIndex(index)) {
            vehicleList.remove(index);
            return index;
        } else {
            return -1;
        }
    }

    public double sumTotalProfit() {
        double totalProfit = 0;
        for (Vehicle v : vehicleList) {
            totalProfit += v.getTotalProfit();
        }
        return totalProfit;
    }

    public void sort(Comparator<Vehicle> comparator) {
        vehicleList.sort(comparator);
    }

    public void display() {
        System.out.printf("%s\t%7s\t%20s\t%10s\t%s\t%s\t%6s\t%6s\t%s\t%5s\t%s\n",
                Column.ID.getName(),
                Column.TYPE.getName(),
                Column.MODEL_NAME.getName(),
                Column.NUMBER.getName(),
                Column.WEIGHT.getName(),
                Column.YEAR.getName(),
                Column.MILEAGE.getName(),
                Column.COLOR.getName(),
                Column.INCOME.getName(),
                Column.TAX.getName(),
                Column.PROFIT.getName());
        for (Vehicle v : vehicleList) {
            System.out.printf("%d\t%7s\t%20s\t%10s\t%d\t%8d\t%6d\t%6s\t%.2f\t%.2f\t%.2f\n",
                    v.getId(),
                    v.getType().getName(),
                    v.getModelName(),
                    v.getRegistrationNumber(),
                    v.getWeightKg(),
                    v.getManufactureYear(),
                    v.getMileage(),
                    v.getColor(),
                    v.getTotalIncome(),
                    v.getCalcTaxPerMonth(),
                    v.getTotalProfit());
        }
        System.out.printf(Column.TOTAL.getName() + ": %.2f\n", sumTotalProfit());
    }

    private boolean isIndex(int index) {
        return 0 <= index && index <= vehicleList.size();
    }
}
