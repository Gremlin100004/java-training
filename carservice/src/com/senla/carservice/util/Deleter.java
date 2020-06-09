package com.senla.carservice.util;

import java.util.Arrays;

// класс утилитный, значит, его методы могут быть статическими
public class Deleter {
    // все классы наследуются от Object, поэтому запись 'T extends Object' бессмысленная
    public <T extends Object, S> T[] deleteElementArray(T[] array, S elementObject) {
        for (int i = 0; i < array.length; i++) {
            // чтобы использовать метод equals, он должен быть переопределен у класса,
            // иначе это равносильно сравнению через ==
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