package com.senla.carservice.util;

import java.io.*;

public class ExportUtil {
    public static String SaveCsv(StringBuilder value, String path) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            PrintStream printStream = new PrintStream(fileOutputStream);
            printStream.print(value);
            printStream.close();
            return "save successfully";
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}