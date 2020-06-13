package com.senla.carservice;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestData {

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");

    public TestData() {
    }

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

    public String[] getArrayExecutionStartTime() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        return new String[]{
                format.format(new Date()),
                format.format(new Date()),
                "12.07.2020 14:00",
                "13.07.2020 15:00",
                "14.07.2020 15:00",
                "15.07.2020 15:00",
                "16.07.2020 15:00",
                format.format(new Date()),
                format.format(new Date()),
                format.format(new Date()),
                format.format(new Date()),
                format.format(new Date()),
        };
    }

    public String[] getArrayLeadTime() {
        return new String[]{ "11.06.2020 10:00",
                "11.07.2020 18:00",
                "13.07.2020 10:00",
                "14.07.2020 18:00",
                "15.07.2020 10:00",
                "16.07.2020 10:00",
                "17.07.2020 10:00",
                "18.07.2020 10:00",
                "19.07.2020 10:00",
                "20.07.2020 10:00",
                "11.07.2020 10:00",
                "11.07.2020 10:00"
        };
    }
}