package com.senla.carservice.util;

import java.math.BigDecimal;
import java.util.Calendar;

public class Sort {

    public <T extends Calendar, S> S[] bubbleSort(T[] arrayValue, S[] arrayObject) {
        for (int i = arrayValue.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arrayValue[j].compareTo(arrayValue[j + 1]) > 0) {
                    T temporarilyValue = arrayValue[j];
                    S temporarilyObject = arrayObject[j];
                    arrayValue[j] = arrayValue[j + 1];
                    arrayObject[j] = arrayObject[j + 1];
                    arrayValue[j + 1] = temporarilyValue;
                    arrayObject[j + 1] = temporarilyObject;
                }
            }
        }
        return arrayObject;
    }

    public <T extends String, S> S[] bubbleSort(T[] arrayValue, S[] arrayObject) {
        for (int i = arrayValue.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arrayValue[j].compareTo(arrayValue[j + 1]) > 0) {
                    T temporarilyValue = arrayValue[j];
                    S temporarilyObject = arrayObject[j];
                    arrayValue[j] = arrayValue[j + 1];
                    arrayObject[j] = arrayObject[j + 1];
                    arrayValue[j + 1] = temporarilyValue;
                    arrayObject[j + 1] = temporarilyObject;
                }
            }
        }
        return arrayObject;
    }

    public <T extends BigDecimal, S> S[] bubbleSort(T[] arrayValue, S[] arrayObject) {
        for (int i = arrayValue.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arrayValue[j].compareTo(arrayValue[j + 1]) > 0) {
                    T temporarilyValue = arrayValue[j];
                    S temporarilyObject = arrayObject[j];
                    arrayValue[j] = arrayValue[j + 1];
                    arrayObject[j] = arrayObject[j + 1];
                    arrayValue[j + 1] = temporarilyValue;
                    arrayObject[j + 1] = temporarilyObject;
                }
            }
        }
        return arrayObject;
    }

    public <T extends Integer, S> S[] bubbleSort(T[] arrayValue, S[] arrayObject) {
        for (int i = arrayValue.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arrayValue[j].compareTo(arrayValue[j + 1]) > 0) {
                    T temporarilyValue = arrayValue[j];
                    S temporarilyObject = arrayObject[j];
                    arrayValue[j] = arrayValue[j + 1];
                    arrayObject[j] = arrayObject[j + 1];
                    arrayValue[j + 1] = temporarilyValue;
                    arrayObject[j + 1] = temporarilyObject;
                }
            }
        }
        return arrayObject;
    }
}