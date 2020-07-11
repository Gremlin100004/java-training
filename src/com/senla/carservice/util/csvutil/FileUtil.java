package com.senla.carservice.util.csvutil;

import com.senla.carservice.exception.BusinessException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtil {

    private FileUtil() {
    }

    public static void saveCsv(List<String> arrayValue, String path) {
        try (PrintStream printStream = new PrintStream(new FileOutputStream(path))) {
            arrayValue.forEach(printStream::println);
        } catch (IOException e) {
            throw new BusinessException("export problem");
        }
    }

    public static List<String> getCsv(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException ioException) {
            throw new BusinessException("import problem");
        }
    }
}