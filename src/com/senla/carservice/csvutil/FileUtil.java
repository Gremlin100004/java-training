package com.senla.carservice.csvutil;

import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.PropertyLoader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class FileUtil {
    // не нужно классЛоадер в константу
    // замечания аналогичные как в Serializer
    private static final ClassLoader CLASS_LOADER = PropertyLoader.class.getClassLoader();
    private FileUtil() {
    }

    public static void saveCsv(List<String> arrayValue, String path) {
        try (PrintStream printStream = new PrintStream(new FileOutputStream(
            Objects.requireNonNull(CLASS_LOADER.getResource(path)).getFile()))) {
            arrayValue.forEach(printStream::println);
        } catch (IOException e) {
            throw new BusinessException("export problem");
        }
    }

    public static List<String> getCsv(String path) {
        try {
            return Files.readAllLines(Paths.get(Objects.requireNonNull(CLASS_LOADER.getResource(path)).getFile()));
        } catch (IOException ioException) {
            throw new BusinessException("import problem");
        }
    }
}