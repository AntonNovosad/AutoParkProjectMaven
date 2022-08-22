package by.devincubator.parser.impl;

import by.devincubator.entity.Types;
import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.parser.ParserTypeFromFile;
import by.devincubator.parser.utils.StringUtils;
import by.devincubator.utils.ReadFile;

import java.util.ArrayList;
import java.util.List;

public class ParserTypeFromFileImpl implements ParserTypeFromFile {
    private static final String TYPES_PATH = "./src/main/resources/data/types.csv";

    public ParserTypeFromFileImpl() {
    }

    @InitMethod
    public void init(){
    }

    public List<Types> loadTypes() {
        List<String> list = ReadFile.readFile(TYPES_PATH);
        List<Types> listVehicleType = new ArrayList<>();
        for (String s : list) {
            listVehicleType.add(createType(s));
        }
        return listVehicleType;
    }

    private Types createType(String csvString) {
        Types vehicleType = new Types();
        String[] array = StringUtils.createArrayString(csvString);
        vehicleType.setId(Long.parseLong(array[0]));
        vehicleType.setName(array[1]);
        vehicleType.setCoefTaxes(Double.parseDouble(array[2]));
        return vehicleType;
    }
}
