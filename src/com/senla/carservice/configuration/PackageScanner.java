package com.senla.carservice.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PackageScanner {
    final String packageProject;
    final String sourceFolder;

    public PackageScanner(String packageProject, String sourceFolder) {
        this.packageProject = packageProject;
        this.sourceFolder = sourceFolder;
    }

    public <T> List<Class<? extends T>> getPackageClass() {
        String path = sourceFolder + "/" + packageProject.replace('.', '/');
        List<File> files = getClassFiles(path);
        return getClassByFiles(files);
    }

    private List<File> getClassFiles(String path) {
        List<File> files = new ArrayList<>();
        List<File> clearFiles = new ArrayList<>();
        getFolderFiles(files, new File(path));
        for (File file : files) {
            if (file.isFile()) {
                clearFiles.add(file);
            }
        }
        return clearFiles;
    }

    private void getFolderFiles(List<File> source, File parent) {
        if (!source.contains(parent)) {
            source.add(parent);
        }
        File[] listFiles = parent.listFiles();
        if (listFiles == null) {
            return;
        }
        for (File file : listFiles) {
            if (file.isDirectory()) {
                getFolderFiles(source, file);
            } else {
                if (!source.contains(file)) {
                    source.add(file);
                }
            }
        }
    }
    @SuppressWarnings("unchecked")
    private <T> List<Class<? extends T>> getClassByFiles(List<File> files) {
        List<Class<? extends T>> classes = new ArrayList<>();
        for (File file : files) {
            String className = file.getName().substring(0, file.getName().lastIndexOf("."));
            String folder = getFolderClass(Arrays.asList(file.getParent().split("(/)|(\\\\)")));
            try {
                classes.add((Class<? extends T>) Class.forName(folder + className));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                //TODO : Add logging.
            }
        }
        return classes;
    }

    private String getFolderClass(List<String> folders) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, foldersSize = folders.size(); i < foldersSize; i++) {
            if (i != 0) {
                stringBuilder.append(folders.get(i)).append(".");
            }
        }
        return stringBuilder.toString();
    }
}