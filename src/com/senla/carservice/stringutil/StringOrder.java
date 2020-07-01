package com.senla.carservice.stringutil;

import com.senla.carservice.domain.Order;

import java.util.Collections;
import java.util.List;

public class StringOrder {

    public static String getStringFromOrder(List<Order> orders) {
        final int LENGTH = 167;
        String line = String.format(" %s\n", String.join("", Collections.nCopies(LENGTH, "-")));
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append(String.format("|%-4s|%-10s|%-12s|%-19s|%-28s|%-28s|%-28s|%-10s|%-12s|%-7s|\n",
                "№",
                "Automaker",
                "Model",
                "Registration Number",
                "Creation Time",
                "Execution Start Time",
                "Lead Time",
                "Price",
                "Status",
                "Deleted"));
        stringBuilder.append(line);
        for (int i = 0; i < orders.size(); i++) {
            stringBuilder.append(String.format("|%-4s|%-10s|%-12s|%-19s|%s|%s|%s|%-10s|%-12s|%-7s|\n",
                    i + 1,
                    orders.get(i).getAutomaker(),
                    orders.get(i).getModel(),
                    orders.get(i).getRegistrationNumber(),
                    orders.get(i).getCreationTime(),
                    orders.get(i).getExecutionStartTime(),
                    orders.get(i).getLeadTime(),
                    orders.get(i).getPrice(),
                    orders.get(i).getStatus(),
                    orders.get(i).isDeleteStatus()));
        }
        stringBuilder.append(line);
        return stringBuilder.toString();
    }
}