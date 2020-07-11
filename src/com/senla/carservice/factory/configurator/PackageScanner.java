package com.senla.carservice.factory.configurator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class PackageScanner {
    final String packageProject;
    final String sourceFolder;

    public PackageScanner(String packageProject, String sourceFolder) {
        this.packageProject = packageProject;
        this.sourceFolder = sourceFolder;
    }

    public <T> List<Class<? extends T>> getPackageClass() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null){
            //TODO : Add logging.
            return null;
        }
        String path = packageProject.replace('.', '/');
        List<File> dirs = getDirs(classLoader, path);
        if (dirs == null){
            return null;
        }
        return getClassByDirs(dirs, packageProject);
    }

    private List<File> getDirs(ClassLoader classLoader, String path){
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
//                if (file.getName().contains(".")) {
//                    continue;
//                }
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


}