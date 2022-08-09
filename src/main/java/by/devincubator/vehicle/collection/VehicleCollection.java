package by.devincubator.vehicle.collection;

import by.devincubator.service.MechanicService;
import by.devincubator.utils.ReadFile;
import by.devincubator.vehicle.*;
import by.devincubator.vehicle.comparator.ComparatorByDefectCount;
import by.devincubator.vehicle.comparator.ComparatorByTaxPerMonth;
import by.devincubator.vehicle.engine.AbstractEngine;
import by.devincubator.vehicle.engine.DieselEngine;
import by.devincubator.vehicle.engine.ElectricalEngine;
import by.devincubator.vehicle.engine.GasolineEngine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class VehicleCollection {
    private static final String SEPARATOR_FOR_SPLIT = ",";
    private static final String REGEX_TO_FIND_DOUBLE = "(\")(\\d+)(,)(\\d+)(\")";
    private static final String GASOLINE_ENGINE = "Gasoline";
    private static final String ELECTRICAL_ENGINE = "Electrical";

    private List<VehicleType> vehicleTypeList;
    private List<Vehicle> vehicleList;
    private List<Rent> rentList;

    public VehicleCollection() {
    }

    public VehicleCollection(String vehicleType, String vehicle, String rent) {
        rentList = loadRents(rent);
        vehicleTypeList = loadTypes(vehicleType);
        vehicleList = loadVehicles(vehicle);
    }

    public List<VehicleType> loadTypes(String inFile) {
        List<String> list = ReadFile.readFile(inFile);
        List<VehicleType> listVehicleType = new ArrayList<>();
        for (String s : list) {
            listVehicleType.add(createType(s));
        }
        return listVehicleType;
    }

    public List<Rent> loadRents(String inFile) {
        List<String> list = ReadFile.readFile(inFile);
        List<Rent> listRent = new ArrayList<>();
        for (String s : list) {
            listRent.add(createRent(s));
        }
        return listRent;
    }

    public List<Vehicle> loadVehicles(String inFile) {
        List<String> list = ReadFile.readFile(inFile);
        List<Vehicle> listVehicle = new ArrayList<>();
        for (String s : list) {
            listVehicle.add(createVehicle(s));
        }
        return listVehicle;
    }

    public List<Vehicle> getListBrokenVehicle(MechanicService mechanicService, String fileName) {
        return loadVehicles(fileName)
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

    public Optional<Vehicle> getVehicleWithMaxTas(String path) {
        return Optional.of(loadVehicles(path)
                .stream()
                .max(new ComparatorByTaxPerMonth())
                .get());
    }

    private VehicleType createType(String csvString) {
        VehicleType vehicleType = new VehicleType();
        String[] array = createArrayString(csvString);
        vehicleType.setId(Integer.parseInt(array[0]));
        vehicleType.setName(array[1]);
        vehicleType.setTaxCoefficient(Double.parseDouble(array[2]));
        return vehicleType;
    }

    private Rent createRent(String csvString) {
        Rent rent = new Rent();
        String[] array = createArrayString(csvString);
        rent.setVehicleId(Integer.parseInt(array[0]));
        rent.setDate(createDate(array[1]));
        rent.setPrice(Double.parseDouble(array[array.length - 1]));
        return rent;
    }

    private Vehicle createVehicle(String csvString) {
        Vehicle vehicle = new Vehicle();
        String[] array = createArrayString(csvString);
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

    private String[] createArrayString(String line) {
        String newLine = replaceString(line);
        return newLine.split(SEPARATOR_FOR_SPLIT);
    }

    private String replaceString(String str) {
        String regex = REGEX_TO_FIND_DOUBLE;
        return str.replaceAll(regex, "$2" + "." + "$4");
    }

    private Date createDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date docDate = null;
        try {
            docDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return docDate;
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

    private boolean isIndex(int index) {
        return 0 <= index && index <= vehicleList.size();
    }
}
