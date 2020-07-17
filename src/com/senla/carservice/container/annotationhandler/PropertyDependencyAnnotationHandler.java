package com.senla.carservice.container.annotationhandler;

import com.senla.carservice.container.annotation.Property;
import com.senla.carservice.enumeration.DefaultValue;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.PropertyLoader;

import java.lang.reflect.Field;

// вроде бы написал в чате, что механизм установки настроек не зависит от механизма инъекции зависимостей
// то есть это разные модули
// учитывая, что мультимодульные проекты мы не делали, стоит хотя бы сделать два механизма в разных
// пакетах, чтобы имитировать разные модули
// но у тебя они лежат в одном пакете
public class PropertyDependencyAnnotationHandler {

    // этот метод может не возвращать ничего - он работает со входящим объектом
    public <T> T configure(T inputObject) {
        Class<?> implementClass = inputObject.getClass();
        // в джава не принято объявлять все переменные в начале метода
        // отдельно от инициализации, где в этом нет необходимости, я говорил об этом на созвоне
        String propertyFileName;
        String value;
        String propertyName;
        String fieldType;
        Property annotation;
        for (Field field : implementClass.getDeclaredFields()) {
            // проще field.isAnnotationPresent()
            annotation = field.getAnnotation(Property.class);
            if (annotation == null) {
                continue;
            }
            propertyFileName = getPropertyFileName(annotation);
            // литералы в константы
            propertyName = getPropertyName(annotation, inputObject.getClass().getName() + "." + field.getName());
            // что такое 1? почему не 2 или 3? может, можно переписать проще?
            // и зачем эти вычисления делать тут? почему не передать аннотацию и поле в метод, и не
            // вычислять там?
            // у класса можно получить простое имя с помощью специального метода без этих хитрых вычислений
            fieldType = getTypeField(annotation, field.getType().getName()
                .substring(field.getType().getName().lastIndexOf('.') + 1));
            value = PropertyLoader.getPropertyValue(propertyFileName, propertyName);
            field.setAccessible(true);
            injectValueInField(field, value, fieldType, inputObject);
        }
        return inputObject;
    }

    private String getPropertyFileName(Property annotation) {
        if (annotation.configName().isEmpty()) {
            // зависимость от проекта
            return DefaultValue.PROPERTY_FILE_NAME.toString();
        } else {
            return annotation.propertyName();
        }
    }

    // для второго аргумента лучше выбрать имя со словом дефолт
    private String getPropertyName(Property annotation, String propertyName) {
        if (annotation.propertyName().isEmpty()) {
            return propertyName;
        } else {
            return annotation.propertyName();
        }
    }

    // имя метода переводится на русский как "поле типа"
    // для второго аргумента лучше выбрать имя со словом дефолт
    private String getTypeField(Property annotation, String typeInString) {
        if (annotation.type().isEmpty()) {
            return typeInString;
        } else {
            return annotation.type();
        }
    }

    private <T> void injectValueInField(Field field, String value, String fieldType, T inputObject) {
        try {
            if (fieldType.equals("Boolean")) {
                field.set(inputObject, Boolean.parseBoolean(value));
            } else if (fieldType.equals("Short")) {
                field.set(inputObject, Short.parseShort(value));
            } else if (fieldType.equals("Integer")) {
                // а если не распарсится, приложение упадет с NumberFormatException?
                field.set(inputObject, Integer.parseInt(value));
            } else if (fieldType.equals("Long")) {
                field.set(inputObject, Long.parseLong(value));
            } else if (fieldType.equals("Double")) {
                field.set(inputObject, Double.parseDouble(value));
            } else if (fieldType.equals("String")) {
                field.set(inputObject, value);
            }
        } catch (IllegalAccessException e) {
            throw new BusinessException("Error set value to a field");
        }
    }
}