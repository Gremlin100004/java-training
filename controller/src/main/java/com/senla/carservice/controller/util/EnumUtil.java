package com.senla.carservice.controller.util;

import java.util.stream.Stream;

public class EnumUtil {

    public static <E extends Enum<E>> boolean isValidEnum(Enum<E>[] enumValues, String stringEnum) {
        return Stream.of(enumValues).anyMatch(enumValue -> enumValue.name().equals(stringEnum));
    }

}
