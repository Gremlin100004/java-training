package com.senla.carservice.container.configurator;

import com.senla.carservice.exception.BusinessException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PackageScanner {
    private final String packageProject;

    public PackageScanner(String packageProject) {
        this.packageProject = packageProject;
    }

    public List<Class<?>> getArrayClasses() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            throw new BusinessException("ClassLoader error");
        }
        URL url = classLoader.getResource(packageProject.replace('.', '/'));
        if (url == null) {
            throw new BusinessException("Error project package");
        }
        String fullPath;
        try {
            fullPath = url.toURI().getPath();
        } catch (URISyntaxException e) {
            throw new BusinessException("Error project package");
        }
        return getClassByPath(getStringFilesPaths(fullPath));
    }

    private List<Class<?>> getClassByPath(List<String> filesStringPaths) {
        List<Class<?>> classes = new ArrayList<>();
        String separator = packageProject.replace(".", "/");
        filesStringPaths.stream()
            .map(file -> file.substring(file.lastIndexOf(separator)).split("\\.")[0].replace("/", "."))
            .forEach(className -> {
                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    throw new BusinessException("Error name class");
                }
            });
        return classes;
    }

    private static List<String> getStringFilesPaths(String stringPath) {
        Path start = Paths.get(stringPath);
        try (Stream<Path> filesPath = Files.walk(start, Integer.MAX_VALUE)) {
            return filesPath
                .filter(Files::isRegularFile)
                .map(String::valueOf)
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new BusinessException("Error scanning package");
        }
    }
}