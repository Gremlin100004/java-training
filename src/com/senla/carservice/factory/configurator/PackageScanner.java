package com.senla.carservice.factory.configurator;

import java.io.File;
import java.io.IOException;
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
    final String packageProject;

    public PackageScanner(String packageProject) {
        this.packageProject = packageProject;
    }

    public <T> List<Class<? extends T>> getPackageClass() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            //TODO : Add logging.
            return null;
        }
        String path = packageProject.replace('.', '/');
        List<File> dirs = getDirs(classLoader, path);
        if (dirs == null) {
            return null;
        }
        return getClassByDirs(dirs, packageProject);
    }

    private List<File> getDirs(ClassLoader classLoader, String path) {
        List<File> dirs = new ArrayList<>();
        try {
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                dirs.add(new File(resource.getFile()));
            }
            return dirs;
        } catch (IOException e) {
            e.printStackTrace();
            //TODO : Add logging.
            return null;
        }
    }

    private <T> List<Class<? extends T>> getClassByDirs(List<File> dirs, String packageName) {
        List<Class<? extends T>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    @SuppressWarnings("unchecked")
    private static <T> List<Class<? extends T>> findClasses(File directory, String packageName) {
        List<Class<? extends T>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                try {
                    classes.add((Class<? extends T>) Class
                        .forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    //TODO : Add logging.
                }
            }
        }
        return classes;
    }

    public static void main(String[] args) throws IOException {

        Path start = Paths.get("C:\\data\\");
        try (Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
            List<String> collect = stream
                    .map(String::valueOf)
                    .sorted()
                    .collect(Collectors.toList());

            collect.forEach(System.out::println);
        }


    }
}