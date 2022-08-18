package by.devincubator.parser.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
    private static final String SEPARATOR_FOR_SPLIT = ",";
    private static final String REGEX_TO_FIND_DOUBLE = "(\")(\\d+)(,)(\\d+)(\")";

    public static String[] createArrayString(String line) {
        String newLine = replaceString(line);
        return newLine.split(SEPARATOR_FOR_SPLIT);
    }

    private static String replaceString(String str) {
        String regex = REGEX_TO_FIND_DOUBLE;
        return str.replaceAll(regex, "$2" + "." + "$4");
    }

    public static Date createDate(String date) {
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
}
