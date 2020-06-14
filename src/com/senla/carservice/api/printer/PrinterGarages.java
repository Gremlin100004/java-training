package com.senla.carservice.api.printer;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Place;

import java.util.Arrays;

public class PrinterGarages {
    public static void printGarages(Garage[] garages){
        final int LENGTH = 57;
        char line = '-';
        char [] arrayChar = new char[LENGTH];
        Arrays.fill(arrayChar, line);
        StringBuilder stringBuilder = new StringBuilder(String.format(" %s\n", String.valueOf(arrayChar)));
        stringBuilder.append(String.format("|%-3s|%-20s|%-13s|%-18s|\n",
                "№", "Name", "Number places", "Number free places"
        ));
        stringBuilder.append(String.format("|%s|\n", String.valueOf(arrayChar)));
        for (int i=0; i < garages.length; i++){
            stringBuilder.append(String.format("|%-3s|%-20s|%-13s|%-18s|\n",
                    i +1, garages[i].getName(),
                    garages[i].getPlaces().length,
                    getnumberFreePlace(garages[i])
            ));
        }
        stringBuilder.append(String.format(" %s", String.valueOf(arrayChar)));
        System.out.println(stringBuilder.toString());
    }

    private static int getnumberFreePlace(Garage garage){
        int numberFreePlace = 0;
        for (Place place : garage.getPlaces()){
            if (!place.isBusyStatus()){
                numberFreePlace++;
            }
        }
        return numberFreePlace;
    }
}