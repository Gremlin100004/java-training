package com.senla.carservice.factory.configurator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PackageScanner {
    // модификатор доступа?
    final String packageProject;

    public PackageScanner(String packageProject) {
        this.packageProject = packageProject;
    }

    // имя метода переводится как "дай пакеты класса", не думаю, что ты так хотел назвать
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

    // что такое dirs?
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
            // а подменить на свое кастомное исключение?
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
    // вместо ? extends T можно использовать вайлдкард
    // от анчекед не избавит, но методы станут проще
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
                // пожалуйста, почитай, как работают ассерты
                // если это копипаста кода, то очень плохо, что ты не разобрал, что как и зачем здесь
                assert !file.getName().contains(".");
                // лично я категорически против рекурсии, это плохой паттерн в промышленном
                // программировании
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                try {
                    classes.add((Class<? extends T>) Class
                        .forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
                } catch (ClassNotFoundException e) {
                    // происл не писать e.printStackTrace();
                    // злоупотребляешь моей рекомендацией по поводу тудушек
                    // если что-то пошло не так при инициализации приложения, приложение не должно стартовать
                    // иначе будет ошибка за ошибкой
                    e.printStackTrace();
                    //TODO : Add logging.
                }
            }
        }
        return classes;
    }
}