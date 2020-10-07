package com.senla.carservice.csv.util;

import com.senla.carservice.csv.exception.CsvException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Slf4j
public final class FileUtil {

    public static void saveCsv(List<String> arrayValue, String path) {
        log.debug("Method saveCsv");
        log.trace("Parameter arrayValue: {}, path: {}", arrayValue, path);
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
            log.error(e.getMessage());
            throw new CsvException("export problem");
        }
    }

    public static List<String> getCsv(String path) {
        log.debug("Method getCsv");
        log.trace("Parameter path: {}", path);
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
            log.error(e.getMessage());
            throw new CsvException("import problem");
        }
    }

}
