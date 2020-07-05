package com.senla.carservice.configuration;

import com.senla.carservice.util.PropertyLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PackageScanner {
    final String packageProject;
    final static String SOURCE_FOLDER = PropertyLoader.getPropertyValue("sourceFolder");

    public PackageScanner(String packageProject) {
        this.packageProject = packageProject;
    }

//    public void getImplementedClass(Class<T> classInterface){
    public <T> List<Class> getImplementedClass(T classInterface){
        String path = String.format("%s/%s", SOURCE_FOLDER, packageProject.replace('.', '/'));
        List<File> files = getClassFiles(path);
        return getClassByType(files, classInterface);
    }

    public List<File> getClassFiles(String path) {
        List<File> files = new ArrayList<>();
        List<File> clearFiles = new ArrayList<>();
        getFolderFiles(files, new File(path));
        for (File file : files) {
            if (file.isFile()){
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
        if(listFiles == null) {
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

    private <T> List<Class> getClassByType(List<File> files, T classInterface){
        List<Class> classes = new ArrayList<>();
        for (File file : files){
            String className = file.getName().substring(0, file.getName().lastIndexOf("."));
            String folder = getFolderClass(Arrays.asList(file.getParent().split("/")));
            try {
                Class classObject = Class.forName( folder + className);
                if (isEqualClass(classObject, classInterface)){
                    classes.add(classObject);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }
    private String getFolderClass(List<String> folders){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, foldersSize = folders.size(); i < foldersSize; i++) {
            if (i != 0) {
                stringBuilder.append(folders.get(i)).append(".");
            }
        }
        return stringBuilder.toString();
    }

    private <T> boolean isEqualClass(T classOne, T classTwo){
        return classOne.equals(classTwo);
    }




//    private void getResource(){
//        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//        String path = packageProject.replace('.', '/');
//        Enumeration<URL> resources = null;
//        try {
//            resources = classLoader.getResources(path);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (resources == null){
//            return;
//        }
//        while (resources.hasMoreElements()) {
//            URL resource = resources.nextElement();
//            System.out.println(resource);
//            File file = null;
//            try {
//                file = new File(resource.toURI());
//
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//            getFileName(file);
//
//        }
//    }
//    private void getFileName(File file){
//        if (file != null){
//            return;
//        }
//        assert false;
//        for(File classFile : file.listFiles()) {
//            String fileName = classFile.getName();
//            System.out.println(fileName);
//        }
//    }

}
