package com.senla.carservice.util;

import java.util.Arrays;

public class Deleter {
    public <T extends Object, S> T[] deleteElementArray(T[] array, S elementObject) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(elementObject)) {
                if (array.length - 1 - i >= 0) {
                    System.arraycopy(array, i + 1, array, i, array.length - 1 - i);
                    array = Arrays.copyOf(array, array.length - 1);
                    break;
                }
            }
        }
        return array;
    }
}