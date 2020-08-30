package com.senla.carservice.configurator;

import com.senla.carservice.exception.InitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PackageScanner {
    private final String packageProject;
    private static final char REPLACEMENT_CHARACTER = '.';
    private static final char CHARACTER_TO_INSERT = '/';
    private static final String CLASS_REPLACEMENT_CHARACTER = ".class";
    private static final String CLASS_CHARACTER_TO_INSERT = "";
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageScanner.class);


    public PackageScanner(String packageProject) {
        LOGGER.debug("Class get parameter packageProject: {}", packageProject);
        this.packageProject = packageProject;
    }

    public List<Class<?>> getClasses() {
        LOGGER.debug("Method getClasses");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            LOGGER.error("classLoader = null");
            throw new InitializationException("ClassLoader error");
        }
        Enumeration<URL> urls;
        try {
            urls = classLoader.getResources(packageProject.replace(REPLACEMENT_CHARACTER, CHARACTER_TO_INSERT));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new InitializationException("ClassLoader error");
        }
        if (urls == null) {
            LOGGER.error("urls == null");
            throw new InitializationException("Error project package");
        }
        List<Class<?>> classes = new ArrayList<>();
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            if (url == null) {
                LOGGER.error("url == null");
                throw new InitializationException("Error project package");
            }
            try {
                String fullPath = url.toURI().getPath();
                classes.addAll(getClassByPath(getStringFilesPaths(fullPath)));
            } catch (URISyntaxException e) {
                LOGGER.error(e.getMessage());
                throw new InitializationException("Error project package");
            }
        }
        return classes;
    }

    public String getPackageProject() {
        return packageProject;
    }

    private List<Class<?>> getClassByPath(List<String> filesStringPaths) {
        LOGGER.debug("Method getClassByPath");
        LOGGER.debug("Parameter filesStringPaths: {}", filesStringPaths);
        return filesStringPaths.stream().map(file -> file.replace(CHARACTER_TO_INSERT, REPLACEMENT_CHARACTER)
            .substring(file.replace(CHARACTER_TO_INSERT, REPLACEMENT_CHARACTER).lastIndexOf(packageProject))
            .replace(CLASS_REPLACEMENT_CHARACTER, CLASS_CHARACTER_TO_INSERT)).map(className -> {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                LOGGER.error(e.getMessage());
                throw new InitializationException("Error name class");
            }
        }).collect(Collectors.toList());
    }

    private static List<String> getStringFilesPaths(String stringPath) {
        LOGGER.debug("Method getStringFilesPaths");
        LOGGER.debug("Parameter stringPath: {}", stringPath);
        try (Stream<Path> filesPath = Files.walk(Paths.get(stringPath), Integer.MAX_VALUE)) {
            return filesPath.filter(Files::isRegularFile).map(String::valueOf).collect(Collectors.toList());
        } catch (IOException e) {
            throw new InitializationException("Error scanning package");
        }
    }
}