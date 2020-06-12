package com.senla.carservice;

import com.senla.carservice.repository.Order;

import java.util.Arrays;

public class Printer {

    public static void printOrder(Order[] orders) {
        final int LENGTH = 162;
        char line = '-';
        char [] arrayChar = new char[LENGTH];
        Arrays.fill(arrayChar, line);
        StringBuilder stringBuilder = new StringBuilder(String.format(" %s\n", String.valueOf(arrayChar)));
        stringBuilder.append(String.format("|%-10s|%-12s|%-19s|%-28s|%-28s|%-28s|%-10s|%-12s|%-7s|\n",
                "Automaker",
                "Model",
                "Registration Number",
                "Creation Time",
                "Execution Start Time",
                "Lead Time",
                "Price",
                "Status",
                "Deleted"));
        stringBuilder.append(String.format("|%s|\n", String.valueOf(arrayChar)));
        for (Order order: orders){
            stringBuilder.append(String.format("|%-10s|%-12s|%-19s|%s|%s|%s|%-10s|%-12s|%-7s|\n",
                    order.getCar().getAutomaker(),
                    order.getCar().getModel(),
                    order.getCar().getRegistrationNumber(),
                    order.getCreationTime(),
                    order.getExecutionStartTime(),
                    order.getLeadTime(),
                    order.getPrice(),
                    order.getStatus(),
                    order.isDeleteStatus()));
        }
        stringBuilder.append(String.format(" %s", String.valueOf(arrayChar)));
        System.out.println(stringBuilder.toString());
        }
    }
