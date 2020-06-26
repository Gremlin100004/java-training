package com.senla.carservice.ui.printer;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Place;

import java.util.Collections;
import java.util.List;

public class PrinterGarages {
    public static void printGarages(List<Garage> garages) {
        final int LENGTH = 57;
        String line = String.format(" %s\n", String.join("", Collections.nCopies(LENGTH, "-")));
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append(String.format("|%-3s|%-20s|%-13s|%-18s|\n",
                "№", "Name", "Number places", "Number free places"
        ));
        stringBuilder.append(line);
        for (int i = 0; i < garages.size(); i++) {
            stringBuilder.append(String.format("|%-3s|%-20s|%-13s|%-18s|\n",
                    i + 1, garages.get(i).getName(),
                    garages.get(i).getPlaces().size(),
                    getnumberFreePlace(garages.get(i))
            ));
        }
        stringBuilder.append(line);
        System.out.println(stringBuilder.toString());
    }

    // нейминг
    private static int getnumberFreePlace(Garage garage) {
        int numberFreePlace = 0;
        for (Place place : garage.getPlaces()) {
            if (!place.isBusyStatus()) {
                numberFreePlace++;
            }
        }
        return numberFreePlace;
    }
}