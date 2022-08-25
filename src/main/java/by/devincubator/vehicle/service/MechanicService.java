package by.devincubator.vehicle.service;

import by.devincubator.entity.Vehicles;
import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.parser.ParserBreakingsFromFile;
import by.devincubator.utils.ReadFile;
import by.devincubator.utils.WriteFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MechanicService implements Fixer {
    private static final String PATH_ORDERS_FILE = "./src/main/resources/data/orders.csv";
    private static final int MIN_NUMBER_OF_BROKEN = 0;
    private static final int MAX_NUMBER_OF_BROKEN = 4;
    private static final int MIN_NUMBER_OF_DETAILS = 1;
    private static final int MAX_NUMBER_OF_DETAILS = 3;
    private static final String REGEX = ".+";
    private static String[] details = {"Фильтр", "Втулка", "Вал", "Ось", "Свечка", "Масло", "ГРМ", "ШРУС"};
    @Autowired
    private ParserBreakingsFromFile parser;

    public void setParser(ParserBreakingsFromFile parser) {
        this.parser = parser;
    }

    @Override
    public Map<String, Integer> detectBreaking(Vehicles vehicle) {
        Map<String, Integer> map = new HashMap<>();
        fillMap(map);
        if (!map.isEmpty()) {
            String line = createStringFromMap(map, vehicle);
            WriteFile.writeString(line, PATH_ORDERS_FILE);
        }
        return map;
    }

    @Override
    public void repair(Vehicles vehicle) {
        List<String> list = parser.loadOrderList();
        if (isBroken(vehicle)) {
            list.removeIf(i -> i.matches(vehicle.getId() + REGEX));
        }
        WriteFile.writeList(list, PATH_ORDERS_FILE);
    }

    @Override
    public boolean isBroken(Vehicles vehicle) {
        List<String> list = parser.loadOrderList();
        for (String str : list) {
            if (str.matches(vehicle.getId() + REGEX)) {
                return true;
            }
        }
        return false;
    }

    private void fillMap(Map<String, Integer> map) {
        int numberOfBroken = getRandomInteger(MIN_NUMBER_OF_BROKEN, MAX_NUMBER_OF_BROKEN);
        int counter = 0;
        while (counter != numberOfBroken) {
            String randomDetails = details[getRandomInteger(0, details.length - 1)];
            int numberOfDetails = getRandomInteger(MIN_NUMBER_OF_DETAILS, MAX_NUMBER_OF_DETAILS);
            map.put(randomDetails, numberOfDetails);
            counter++;
        }
    }

    private String createStringFromMap(Map<String, Integer> map, Vehicles vehicle) {
        String line = String.valueOf(vehicle.getId());
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            line = line + "," + entry.getKey() + "," + entry.getValue();
        }
        line = line + "\n";
        return line;
    }

    private static int getRandomInteger(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1) + min);
    }

    public void showVehicleWithoutBrokenDetails(List<Vehicles> listVehicle) {
        System.out.println("Vehicle without broken details:");
        listVehicle.stream()
                .filter(vehicle -> !isBroken(vehicle))
                .forEach(System.out::println);
    }

    public void showVehicleWithMaxBrokenDetails(List<Vehicles> listVehicle) {
        List<String> list = parser.loadOrderList();
        int max = findMaxNumberBrokenDetails();
        System.out.println("Vehicle with max broken details:");
        list.forEach(s -> {
            if (max == findNumberBrokenDetails(s)) {
                listVehicle
                        .stream()
                        .filter(vehicle -> s.matches(vehicle.getId() + REGEX))
                        .forEach(System.out::println);
            }
        });
    }

    private int findMaxNumberBrokenDetails() {
        List<String> list = parser.loadOrderList();
        int max = 0;
        for (String str : list) {
            if (max < findNumberBrokenDetails(str)) {
                max = findNumberBrokenDetails(str);
            }
        }
        return max;
    }

    private int findNumberBrokenDetails(String str) {
        int numberOfDetail = 0;
        String[] array = str.split(",");
        for (int i = 1; i < array.length; i++) {
            if (array[i].matches("[1-3]")) {
                numberOfDetail += Integer.parseInt(array[i]);
            }
        }
        return numberOfDetail;
    }

    public static int getDefectCount(Vehicles vehicle) {
        List<String> list = ReadFile.readFile(PATH_ORDERS_FILE);
        int numberOfDetail = 0;
        for (String str : list) {
            if (str.matches(vehicle.getId() + REGEX)) {
                String[] array = str.split(",");
                for (int i = 1; i < array.length; i++) {
                    if (array[i].matches("[1-3]")) {
                        numberOfDetail += Integer.parseInt(array[i]);
                    }
                }
            }
        }
        return numberOfDetail;
    }
}
