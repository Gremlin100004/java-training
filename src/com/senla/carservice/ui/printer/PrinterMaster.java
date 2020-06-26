package com.senla.carservice.ui.printer;

import com.senla.carservice.domain.Master;

import java.util.Collections;
import java.util.List;

public class PrinterMaster {
    public static void printMasters(List<Master> masters) {
        // это не константа, это локальная переменная, у нее не может быть такой нейминг
        // константа - это "публичная/приватная статическая финальная переменная КЛАССА"
        final int LENGTH = 38;
        String line = String.format(" %s\n", String.join("", Collections.nCopies(LENGTH, "-")));
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append(String.format("|%-3s|%-20s|%-13s|\n",
                "№", "Name",
                "Number order"
        ));
        stringBuilder.append(line);
        for (int i = 0; i < masters.size(); i++) {
            if (masters.get(i).getNumberOrder() == null) {
                stringBuilder.append(String.format("|%-3s|%-20s|%-13s|\n",
                        i + 1, masters.get(i).getName(), "0"
                ));
            } else {
                stringBuilder.append(String.format("|%-3s|%-20s|%-13s|\n",
                        i + 1, masters.get(i).getName(),
                        masters.get(i).getNumberOrder()
                ));
            }
        }
        stringBuilder.append(line);
        System.out.println(stringBuilder.toString());
    }
}