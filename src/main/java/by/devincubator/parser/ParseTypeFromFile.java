package by.devincubator.parser;

import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.parser.utils.StringUtils;
import by.devincubator.utils.ReadFile;
import by.devincubator.vehicle.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class ParseTypeFromFile {
    private static final String TYPES_PATH = "./src/main/resources/data/types.csv";

    public ParseTypeFromFile() {
    }

    @InitMethod
    public void init(){
    }

    public List<VehicleType> loadTypes() {
        List<String> list = ReadFile.readFile(TYPES_PATH);
        List<VehicleType> listVehicleType = new ArrayList<>();
        for (String s : list) {
            listVehicleType.add(createType(s));
        }
        return listVehicleType;
    }

    private VehicleType createType(String csvString) {
        VehicleType vehicleType = new VehicleType();
        String[] array = StringUtils.createArrayString(csvString);
        vehicleType.setId(Integer.parseInt(array[0]));
        vehicleType.setName(array[1]);
        vehicleType.setTaxCoefficient(Double.parseDouble(array[2]));
        return vehicleType;
    }
}
