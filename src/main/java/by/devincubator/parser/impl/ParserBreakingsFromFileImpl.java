package by.devincubator.parser.impl;

import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.parser.ParserBreakingsFromFile;
import by.devincubator.utils.ReadFile;

import java.util.List;

public class ParserBreakingsFromFileImpl implements ParserBreakingsFromFile {
    private static final String PATH_ORDERS_FILE = "./src/main/resources/data/orders.csv";

    public ParserBreakingsFromFileImpl() {
    }

    @InitMethod
    public void init() {
    }

    public List<String> loadOrderList() {
        return ReadFile.readFile(PATH_ORDERS_FILE);
    }
}
