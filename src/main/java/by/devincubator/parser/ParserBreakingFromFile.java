package by.devincubator.parser;

import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.utils.ReadFile;

import java.util.List;

public class ParserBreakingFromFile {
    private static final String PATH_ORDERS_FILE = "./src/main/resources/data/orders.csv";

    public ParserBreakingFromFile() {
    }

    @InitMethod
    public void init() {
    }

    public List<String> loadOrderList() {
        return ReadFile.readFile(PATH_ORDERS_FILE);
    }
}
