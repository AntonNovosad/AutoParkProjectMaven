package by.devincubator.parser.impl;

import by.devincubator.entity.Rents;
import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.parser.ParserRentFromFile;
import by.devincubator.parser.utils.StringUtils;
import by.devincubator.utils.ReadFile;

import java.util.ArrayList;
import java.util.List;

public class ParserRentFromFileImpl implements ParserRentFromFile {
    private static final String RENTS_PATH = "./src/main/resources/data/rents.csv";
    private static Long id = 1l;

    public ParserRentFromFileImpl() {
    }

    @InitMethod
    public void init() {
    }

    public List<Rents> loadRents() {
        List<String> list = ReadFile.readFile(RENTS_PATH);
        List<Rents> listRent = new ArrayList<>();
        for (String s : list) {
            listRent.add(createRent(s));
        }
        return listRent;
    }

    private Rents createRent(String csvString) {
        Rents rent = new Rents();
        String[] array = StringUtils.createArrayString(csvString);
        rent.setId(id++);
        rent.setVehicleId(Long.parseLong(array[0]));
        rent.setDate("'" + StringUtils.createDate(array[1]).toString() + "'");
        rent.setPrice(Double.parseDouble(array[array.length - 1]));
        return rent;
    }
}
