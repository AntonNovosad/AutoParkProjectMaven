package by.devincubator.parser;

import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.parser.utils.StringUtils;
import by.devincubator.utils.ReadFile;
import by.devincubator.vehicle.Rent;

import java.util.ArrayList;
import java.util.List;

public class ParseRentFromFile {
    private static final String RENTS_PATH = "./src/main/resources/data/rents.csv";

    public ParseRentFromFile() {
    }

    @InitMethod
    public void init(){
    }

    public List<Rent> loadRents() {
        List<String> list = ReadFile.readFile(RENTS_PATH);
        List<Rent> listRent = new ArrayList<>();
        for (String s : list) {
            listRent.add(createRent(s));
        }
        return listRent;
    }

    private Rent createRent(String csvString) {
        Rent rent = new Rent();
        String[] array = StringUtils.createArrayString(csvString);
        rent.setVehicleId(Integer.parseInt(array[0]));
        rent.setDate(StringUtils.createDate(array[1]));
        rent.setPrice(Double.parseDouble(array[array.length - 1]));
        return rent;
    }
}
