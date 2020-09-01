package com.senla.carservice.util;

import com.senla.carservice.exception.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {
    }

    public static void saveCsv(List<String> arrayValue, String path) {
        LOGGER.debug("Method saveCsv");
        LOGGER.debug("Parameter arrayValue: {}", arrayValue);
        LOGGER.debug("Parameter path: {}", path);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            throw new CsvException("export problem");
        }
        URL url = classLoader.getResource(path);
        if (url == null) {
            throw new CsvException("export problem");
        }
        try (PrintStream printStream = new PrintStream(new FileOutputStream(String.valueOf(Path.of(url.toURI()))))) {
            arrayValue.forEach(printStream::println);
        } catch (IOException | URISyntaxException e) {
            LOGGER.error(e.getMessage());
            throw new CsvException("export problem");
        }
    }

    public static List<String> getCsv(String path) {
        LOGGER.debug("Method getCsv");
        LOGGER.debug("Parameter path: {}", path);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        List<String> csvLines = new ArrayList<>();
        if (classLoader == null) {
            return csvLines;
        }
        URL url = classLoader.getResource(path);
        if (url == null) {
            return csvLines;
        }
        try {
            return Files.readAllLines(Path.of(url.toURI()));
        } catch (IOException | URISyntaxException e) {
            LOGGER.error(e.getMessage());
            throw new CsvException("import problem");
        }
    }
}