package com.senla.carservice.stringutil;

import com.senla.carservice.domain.Master;

import java.util.Collections;
import java.util.List;

public class StringMaster {
    private static final int LENGTH = 38;

    public static String getStringFromMasters(List<Master> masters) {
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
        return stringBuilder.toString();
    }
}