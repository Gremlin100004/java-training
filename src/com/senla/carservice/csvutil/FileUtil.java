package com.senla.carservice.csvutil;

import com.senla.carservice.exception.ExportException;
import com.senla.carservice.exception.NumberObjectZeroException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtil {

    private FileUtil() {
    }

    public static void saveCsv(String value, String path, boolean flag) {
        try (PrintStream printStream = new PrintStream(new FileOutputStream(path, flag))) {
            printStream.print(value);
        } catch (IOException e) {
            throw new ExportException("export problem");
        }
    }

    public static List<String> getCsv(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException ioException) {
            throw new NumberObjectZeroException("import problem");
        }
    }
}