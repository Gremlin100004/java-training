package com.senla.carservice.controller.data;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

// тестовые данные никак не могут относиться к слою контроллер
public class Data {

    public String getNameCarService() {
        return "Lottery";
    }

    public String[] getArrayMasterNames() {
        return new String[]{
                "Petya", "Vasya", "Georgiy",
                "Antonina", "Igor", "Dmitriy",
                "Aleksandr", "Jon", "Konstantin",
                "Andrey", "Pasha", "Egor", "Slava",
                "Valera", "Vitalik", "Sergei",
                "Danial", "Ivan", "Nikolai", "Patrik",
                "Semen", "Uriy"
        };
    }

    public String[] getArrayGarageNames() {
        return new String[]{
                "Box One", "Box Two", "Box Three",
                "Box Four", "Box Five"
        };
    }

    public String[] getArrayAutomaker() {
        return new String[]{
                "lexus", "Mercedes", "BMW",
                "Audi", "Opel", "Audi", "Ford",
                "KIA", "Infinity", "Mazda",
                "Chevrolet", "lexus"
        };
    }

    public String[] getArrayModel() {
        return new String[]{
                "LS", "A 200", "X6",
                "A6", "Insignia", "A4", "Escape",
                "Optima", "QX30", "RX7",
                "Epica", "LS"
        };
    }

    public String[] getArrayRegistrationNumber() {
        return new String[]{
                "1234 AB-7", "3234 AB-7", "4444 AB-7",
                "1111 AB-7", "4444 AB-7", "5555 AB-7",
                "6666 AB-7", "7777 AB-7", "8888 AB-7",
                "9999 AB-7", "8484 AB-7", "1919 AB-7"
        };
    }

    public BigDecimal[] getArrayPrice() {
        BigDecimal[] prices = new BigDecimal[10];
        return new BigDecimal[]{
                new BigDecimal("360.99"), new BigDecimal("600.23"),
                new BigDecimal("457.34"), new BigDecimal("10020.99"),
                new BigDecimal("443.65"), new BigDecimal("321.67"),
                new BigDecimal("367.46"), new BigDecimal("642.12"),
                new BigDecimal("735.78"), new BigDecimal("135.39"),
                new BigDecimal("335.62"), new BigDecimal("867.31")
        };
    }

    public GregorianCalendar[] getArrayExecutionStartTime() {
        return new GregorianCalendar[]{
                new GregorianCalendar(),
                new GregorianCalendar(),
                new GregorianCalendar(2020, Calendar.JULY, 12, 14, 0),
                new GregorianCalendar(2020, Calendar.JULY, 13, 15, 0),
                new GregorianCalendar(2020, Calendar.JULY, 14, 15, 0),
                new GregorianCalendar(2020, Calendar.JULY, 15, 15, 0),
                new GregorianCalendar(2020, Calendar.JULY, 16, 15, 0),
                new GregorianCalendar(),
                new GregorianCalendar(),
                new GregorianCalendar(),
                new GregorianCalendar(),
                new GregorianCalendar(),
        };
    }

    public GregorianCalendar[] getArrayLeadTime() {
        return new GregorianCalendar[]{
                new GregorianCalendar(2020, Calendar.JULY, 11, 10, 0),
                new GregorianCalendar(2020, Calendar.JULY, 11, 18, 0),
                new GregorianCalendar(2020, Calendar.JULY, 13, 10, 0),
                new GregorianCalendar(2020, Calendar.JULY, 14, 18, 0),
                new GregorianCalendar(2020, Calendar.JULY, 15, 10, 0),
                new GregorianCalendar(2020, Calendar.JULY, 16, 10, 0),
                new GregorianCalendar(2020, Calendar.JULY, 17, 10, 0),
                new GregorianCalendar(2020, Calendar.JULY, 18, 10, 0),
                new GregorianCalendar(2020, Calendar.JULY, 19, 10, 0),
                new GregorianCalendar(2020, Calendar.JULY, 20, 10, 0),
                new GregorianCalendar(2020, Calendar.JULY, 11, 10, 0),
                new GregorianCalendar(2020, Calendar.JULY, 11, 10, 0),
        };
    }
}