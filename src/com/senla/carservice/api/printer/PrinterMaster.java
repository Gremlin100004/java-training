package com.senla.carservice.api.printer;

import com.senla.carservice.domain.Master;

import java.util.ArrayList;
import java.util.Arrays;

public class PrinterMaster {
    public static void printMasters(ArrayList<Master> masters){
        final int LENGTH = 38;
        char line = '-';
        char [] arrayChar = new char[LENGTH];
        Arrays.fill(arrayChar, line);
        StringBuilder stringBuilder = new StringBuilder(String.format(" %s\n", String.valueOf(arrayChar)));
        stringBuilder.append(String.format("|%-3s|%-20s|%-13s|\n",
                "№", "Name",
                "Number order"
                ));
        stringBuilder.append(String.format("|%s|\n", String.valueOf(arrayChar)));
        for (int i=0; i < masters.size(); i++){
            stringBuilder.append(String.format("|%-3s|%-20s|%-13s|\n",
                    i +1, masters.get(i).getName(),
                    masters.get(i).getNumberOrder()
                    ));
        }
        stringBuilder.append(String.format(" %s", String.valueOf(arrayChar)));
        System.out.println(stringBuilder.toString());
    }
}

