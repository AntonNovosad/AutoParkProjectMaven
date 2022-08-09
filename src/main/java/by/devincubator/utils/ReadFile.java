package by.devincubator.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadFile {

    public static List<String> readFile(String inFile) {
        List<String> list = null;
        try {
            list = Files.readAllLines(Paths.get(inFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
