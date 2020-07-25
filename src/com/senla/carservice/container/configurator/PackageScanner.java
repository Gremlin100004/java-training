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
    private static final char REPLACEMENT_CHARACTER = '.';
    private static final char CHARACTER_TO_INSERT = '/';
    private static final String CLASS_REPLACEMENT_CHARACTER = ".class";
    private static final String CLASS_CHARACTER_TO_INSERT = "";

    public PackageScanner(String packageProject) {
        this.packageProject = packageProject;
    }

    // название метода вводит в заблуждение - кажется, что оно возвращает классы массивов
    // использовать английский порядок слов при нейминге с двумя существительными (главное слово ставится
    // последним)
    // а еще лучше просто getClasses()
    public List<Class<?>> getArrayClasses() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            throw new BusinessException("ClassLoader error");
        }
        URL url = classLoader.getResource(packageProject.replace(REPLACEMENT_CHARACTER, CHARACTER_TO_INSERT));
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

    // я отформатирую
    // старайся избегать смещения кода за счет отступов в конец строки (вправо), потому
    // что рабочая зона как раз находится слева
    private List<Class<?>> getClassByPath(List<String> filesStringPaths) {
        return filesStringPaths
                .stream()
                .map(file -> file.replace(CHARACTER_TO_INSERT, REPLACEMENT_CHARACTER)
                        .substring(file
                                .replace(CHARACTER_TO_INSERT, REPLACEMENT_CHARACTER)
                                .lastIndexOf(packageProject))
                        .replace(CLASS_REPLACEMENT_CHARACTER, CLASS_CHARACTER_TO_INSERT))
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