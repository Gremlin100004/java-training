package com.senla.carservice.ui.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Menu {

    private static final String EXIT_MENU = "0. Exit program\n---------------";
    private static final String END_OF_LINE = "\n";
    private static final String SYMBOL_FOR_JOIN_METHOD = "";
    private static final String LINE_SEPARATOR = "~";
    private static final String START_OF_MENU_BAR = ". ";
    private static final int INDEX_ADDITION = 1;
    @NonNull
    private String name;
    private List<MenuItem> menuItems = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.join(SYMBOL_FOR_JOIN_METHOD, Collections.nCopies(name.length(), LINE_SEPARATOR)))
            .append(END_OF_LINE);
        stringBuilder.append(name).append(END_OF_LINE);
        stringBuilder.append(String.join(SYMBOL_FOR_JOIN_METHOD, Collections.nCopies(name.length(), LINE_SEPARATOR)))
            .append(END_OF_LINE);
        for (int i = 0; i < this.menuItems.size(); i++) {
            stringBuilder.append(i + INDEX_ADDITION).append(START_OF_MENU_BAR).append(menuItems.get(i))
                .append(END_OF_LINE);
        }
        stringBuilder.append(EXIT_MENU);
        return stringBuilder.toString();
    }
}