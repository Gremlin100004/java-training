package com.senla.carservice.ui.util;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TestData {

    // в классе со статическими методами обязательно должен быть пустой конструктор? для чего?
    // если его и делают в утилитных классах, то только с приватным доступом
    public TestData() {
    }

    // почему такой порядок ключевых слов? первым ВСЕГДА пишется модификатор доступа
    // почему ArrayList, а не List?
    static public ArrayList<String> getArrayMasterNames() {
        ArrayList<String> arrayName = new ArrayList<>();
        arrayName.add("Petya");
        arrayName.add("Vasya");
        arrayName.add("Georgiy");
        arrayName.add("Antonina");
        arrayName.add("Igor");
        arrayName.add("Dmitriy");
        arrayName.add("Aleksandr");
        arrayName.add("Jon");
        arrayName.add("Konstantin");
        arrayName.add("Andrey");
        arrayName.add("Pasha");
        arrayName.add("Egor");
        arrayName.add("Slava");
        arrayName.add("Valera");
        arrayName.add("Vitalik");
        arrayName.add("Sergei");
        arrayName.add("Danial");
        arrayName.add("Ivan");
        arrayName.add("Nikolai");
        arrayName.add("Patrik");
        arrayName.add("Semen");
        arrayName.add("Uriy");
        return arrayName;
    }

    static public ArrayList<String> getArrayGarageNames() {
        ArrayList<String> arrayName = new ArrayList<>();
        arrayName.add("Box One");
        arrayName.add("Box Two");
        arrayName.add("Box Three");
        arrayName.add("Box Four");
        arrayName.add("Box Five");
        return arrayName;
    }

    static public ArrayList<String> getArrayAutomaker() {
        ArrayList<String> arrayName = new ArrayList<>();
        arrayName.add("lexus");
        arrayName.add("Mercedes");
        arrayName.add("BMW");
        arrayName.add("Audi");
        arrayName.add("Opel");
        arrayName.add("Audi");
        arrayName.add("Ford");
        arrayName.add("KIA");
        arrayName.add("Infinity");
        arrayName.add("Mazda");
        arrayName.add("Chevrolet");
        arrayName.add("lexus");
        return arrayName;
    }

    static public ArrayList<String> getArrayModel() {
        ArrayList<String> arrayName = new ArrayList<>();
        arrayName.add("LS");
        arrayName.add("A 200");
        arrayName.add("X6");
        arrayName.add("A6");
        arrayName.add("Insignia");
        arrayName.add("A4");
        arrayName.add("Optima");
        arrayName.add("QX30");
        arrayName.add("RX7");
        arrayName.add("Epica");
        arrayName.add("LS");
        arrayName.add("LS");
        return arrayName;
    }

    static public ArrayList<String> getArrayRegistrationNumber() {
        ArrayList<String> arrayName = new ArrayList<>();
        arrayName.add("1234 AB-7");
        arrayName.add("3234 AB-7");
        arrayName.add("4444 AB-7");
        arrayName.add("1111 AB-7");
        arrayName.add("4444 AB-7");
        arrayName.add("5555 AB-7");
        arrayName.add("6666 AB-7");
        arrayName.add("7777 AB-7");
        arrayName.add("8888 AB-7");
        arrayName.add("9999 AB-7");
        arrayName.add("8484 AB-7");
        arrayName.add("1919 AB-7");
        return arrayName;
    }

    static public ArrayList<BigDecimal> getArrayPrice() {
        ArrayList<BigDecimal> arrayPrice = new ArrayList<>();
        arrayPrice.add(new BigDecimal("360.99"));
        arrayPrice.add(new BigDecimal("600.23"));
        arrayPrice.add(new BigDecimal("457.34"));
        arrayPrice.add(new BigDecimal("10020.99"));
        arrayPrice.add(new BigDecimal("443.65"));
        arrayPrice.add(new BigDecimal("321.67"));
        arrayPrice.add(new BigDecimal("367.46"));
        arrayPrice.add(new BigDecimal("642.12"));
        arrayPrice.add(new BigDecimal("735.78"));
        arrayPrice.add(new BigDecimal("135.39"));
        arrayPrice.add(new BigDecimal("335.62"));
        arrayPrice.add(new BigDecimal("867.31"));
        return arrayPrice;
    }

    static public ArrayList<String> getArrayExecutionStartTime() {
        ArrayList<String> arrayDate = new ArrayList<>();
        arrayDate.add("11.07.2020 10:00");
        arrayDate.add("11.07.2020 10:00");
        arrayDate.add("12.07.2020 14:00");
        arrayDate.add("13.07.2020 15:00");
        arrayDate.add("14.07.2020 15:00");
        arrayDate.add("15.07.2020 15:00");
        arrayDate.add("16.07.2020 15:00");
        arrayDate.add("16.07.2020 15:00");
        arrayDate.add("16.07.2020 15:00");
        arrayDate.add("16.07.2020 15:00");
        arrayDate.add("16.07.2020 15:00");
        arrayDate.add("16.07.2020 15:00");
        return arrayDate;
    }

    static public ArrayList<String> getArrayLeadTime() {
        ArrayList<String> arrayDate = new ArrayList<>();
        arrayDate.add("11.07.2020 18:00");
        arrayDate.add("11.07.2020 18:00");
        arrayDate.add("13.07.2020 10:00");
        arrayDate.add("14.07.2020 18:00");
        arrayDate.add("15.07.2020 10:00");
        arrayDate.add("16.07.2020 10:00");
        arrayDate.add("17.07.2020 10:00");
        arrayDate.add("18.07.2020 10:00");
        arrayDate.add("19.07.2020 10:00");
        arrayDate.add("20.07.2020 10:00");
        arrayDate.add("20.07.2020 10:00");
        arrayDate.add("20.07.2020 10:00");
        return arrayDate;
    }
}