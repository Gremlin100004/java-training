package com.senla.carservice.controller.util;

import com.senla.carservice.domain.IOrder;

public class Printer {

    public void printOrder(IOrder[] orders) {
        // тест чего?
        char test = '-';
        // что такое 162? Почему не 165? Почитай про такой антипаттерн "магическое число"
        char [] arrayChar = new char[162];
        for (int i = 0; i < arrayChar.length; i++){
            arrayChar[i] = test;
        }
        StringBuilder stringBuilder = new StringBuilder(" " + String.valueOf(arrayChar) + "\n");
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
        // использовать стринг билдер и конкатенацию одновременно не рекомендуется
        stringBuilder.append("|" + String.valueOf(arrayChar) + "|\n");
        for (IOrder order: orders){
            stringBuilder.append(String.format("|%-10s|%-12s|%-19s|%s|%s|%s|%-10s|%-12s|%-7s|\n",
                    order.getCar().getAutomaker(),
                    order.getCar().getModel(),
                    order.getCar().getRegistrationNumber(),
                    order.getCreationTime().getTime(),
                    order.getExecutionStartTime().getTime(),
                    order.getLeadTime().getTime(),
                    order.getPrice(),
                    order.getStatus(),
                    order.isDeleteStatus()));
        }
        // использовать стринг билдер и конкатенацию одновременно не рекомендуется
        stringBuilder.append(" " + String.valueOf(arrayChar));
        System.out.println(stringBuilder.toString());
        }
    }
