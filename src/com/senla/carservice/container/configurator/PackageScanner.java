package com.senla.carservice.container.configurator;

import com.senla.carservice.exception.BusinessException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PackageScanner {
    private final String packageProject;
    private static final char POINT = '.';
    private static final char SLASH = '/';
    private static final String CLASS_LITERAL = ".class";
    private static final String EMPTY_LITERAL = "";

    public PackageScanner(String packageProject) {
        this.packageProject = packageProject;
    }

    public List<Class<?>> getArrayClasses() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            throw new BusinessException("ClassLoader error");
        }
        URL url = classLoader.getResource(packageProject.replace(POINT, SLASH));
        if (url == null) {
            throw new BusinessException("Error project package");
        }
        try {
            String fullPath = url.toURI().getPath();
            return getClassByPath(getStringFilesPaths(fullPath));
        } catch (URISyntaxException e) {
            throw new BusinessException("Error project package");
        }
    }

    public String getPackageProject() {
        return packageProject;
    }

    private List<Class<?>> getClassByPath(List<String> filesStringPaths) {
        return filesStringPaths.stream()
            .map(file -> file.replace(SLASH, POINT).substring(file.replace(SLASH, POINT).lastIndexOf(packageProject))
                    .replace(CLASS_LITERAL, EMPTY_LITERAL))
            .map(className -> {
                try {
                    return Class.forName(className);
                } catch (ClassNotFoundException e) {
                    throw new BusinessException("Error name class");
                }
            })
            .collect(Collectors.toList());
    }

    private static List<String> getStringFilesPaths(String stringPath) {
        try (Stream<Path> filesPath = Files.walk(Paths.get(stringPath), Integer.MAX_VALUE)) {
            return filesPath
                .filter(Files::isRegularFile)
                .map(String::valueOf)
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new BusinessException("Error scanning package");
        }
    }
}