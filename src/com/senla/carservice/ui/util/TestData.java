package com.senla.carservice.ui.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestData {

    private TestData() {
    }

    public static List<String> getArrayMasterNames() {
        List<String> arrayName = new ArrayList<>();
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

    public static List<Integer> getArrayPlaceNumber() {
        List<Integer> arrayNumber = new ArrayList<>();
        arrayNumber.add(1);
        arrayNumber.add(2);
        arrayNumber.add(3);
        arrayNumber.add(4);
        arrayNumber.add(5);
        arrayNumber.add(6);
        arrayNumber.add(7);
        arrayNumber.add(8);
        arrayNumber.add(9);
        arrayNumber.add(10);
        arrayNumber.add(11);
        arrayNumber.add(12);
        return arrayNumber;
    }

    public static List<String> getArrayAutomaker() {
        List<String> arrayName = new ArrayList<>();
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

    public static List<String> getArrayModel() {
        List<String> arrayName = new ArrayList<>();
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

    public static List<String> getArrayRegistrationNumber() {
        List<String> arrayName = new ArrayList<>();
        arrayName.add("1234 AB-7");
        arrayName.add("3234 AB-7");
        arrayName.add("2222 AB-7");
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

    public static List<BigDecimal> getArrayPrice() {
        List<BigDecimal> arrayPrice = new ArrayList<>();
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

    public static List<String> getArrayExecutionStartTime() {
        List<String> arrayDate = new ArrayList<>();
        arrayDate.add("11.09.2020 10:00");
        arrayDate.add("11.09.2020 10:00");
        arrayDate.add("12.09.2020 14:00");
        arrayDate.add("13.09.2020 15:00");
        arrayDate.add("14.09.2020 15:00");
        arrayDate.add("15.09.2020 15:00");
        arrayDate.add("16.09.2020 15:00");
        arrayDate.add("16.09.2020 15:00");
        arrayDate.add("16.09.2020 15:00");
        arrayDate.add("16.09.2020 15:00");
        arrayDate.add("16.09.2020 15:00");
        arrayDate.add("16.09.2020 15:00");
        return arrayDate;
    }

    public static List<String> getArrayLeadTime() {
        ArrayList<String> arrayDate = new ArrayList<>();
        arrayDate.add("11.09.2020 18:00");
        arrayDate.add("11.09.2020 18:00");
        arrayDate.add("13.09.2020 10:00");
        arrayDate.add("14.09.2020 18:00");
        arrayDate.add("15.09.2020 10:00");
        arrayDate.add("16.09.2020 10:00");
        arrayDate.add("17.09.2020 10:00");
        arrayDate.add("18.09.2020 10:00");
        arrayDate.add("19.09.2020 10:00");
        arrayDate.add("20.09.2020 10:00");
        arrayDate.add("20.09.2020 10:00");
        arrayDate.add("20.09.2020 10:00");
        return arrayDate;
    }
}