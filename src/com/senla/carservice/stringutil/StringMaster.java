package com.senla.carservice.stringutil;

import com.senla.carservice.domain.Master;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class StringMaster {
    private static final int LENGTH = 38;

    public static String getStringFromMasters(List<Master> masters) {
        String line = String.join("", Collections.nCopies(LENGTH, "-")) + "\n";
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append(String.format("|%-3s|%-20s|%-12s|\n",
                                           "№", "Name",
                                           "Number order"
                                          ));
        stringBuilder.append(line);
        int bound = masters.size();
        IntStream.range(0, bound).mapToObj(i -> String.format("|%-3s|%-20s|%-12s|\n",
                                                              i + 1, masters.get(i).getName(),
                                                              masters.get(i).getOrders().size()
                                                             )).forEachOrdered(stringBuilder::append);
        stringBuilder.append(line);
        return stringBuilder.toString();
    }
}