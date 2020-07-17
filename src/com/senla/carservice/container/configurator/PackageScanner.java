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
        // литералы в константы
        URL url = classLoader.getResource(packageProject.replace('.', '/'));
        if (url == null) {
            throw new BusinessException("Error project package");
        }
        // лучше не разбивать логику, а поместить ее всю целиком в трай, я отредактирую код:
        try {
            String fullPath = url.toURI().getPath();
            return getClassByPath(getStringFilesPaths(fullPath));
        } catch (URISyntaxException e) {
            throw new BusinessException("Error project package");
        }
    }

    private List<Class<?>> getClassByPath(List<String> filesStringPaths) {
        List<Class<?>> classes = new ArrayList<>();
        // почему переменная называется разделитель?
        String separator = packageProject.replace(".", "/");
        filesStringPaths.stream()
            // плохо понимаю, зачем менять точку на слэш, а потом слэш снова на точку
            .map(file -> file.substring(file.lastIndexOf(separator)).split("\\.")[0].replace("/", "."))
            // обычно в стримах используют коллекторы для создания листа, а не форыч
            // в данном случае тут будет нужен еще один мап и затем коллект(Коллектор.туЛист())
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