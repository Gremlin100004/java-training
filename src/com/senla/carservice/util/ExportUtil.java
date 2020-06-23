package com.senla.carservice.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ExportUtil {
    public static String SaveCsv(StringBuilder value, String path) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            PrintStream printStream = new PrintStream(fileOutputStream);
            printStream.print(value);
            return "save successfully";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public static List<String> GetCsv(String path) {
        String line;
        List<String> csvLines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            while ((line = bufferedReader.readLine()) != null) {
                csvLines.add(line);
            }
        } catch (IOException e) {
            return csvLines;
        }
        return csvLines;
    }
}