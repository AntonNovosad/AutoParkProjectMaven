package by.devincubator.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class WriteFile {

    public static void writeString(String line, String fileName) {
        try {
            Files.write(Paths.get(fileName), line.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeList(List<String> list, String fileName) {
        try {
            Files.write(Paths.get(fileName), list, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
