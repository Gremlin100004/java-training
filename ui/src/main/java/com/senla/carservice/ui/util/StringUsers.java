package com.senla.carservice.ui.util;

import com.senla.carservice.dto.UserDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class StringUsers {

    private static final int LINE_LENGTH = 55;
    private static final String SYMBOL_FOR_JOIN_METHOD = "";
    private static final String START_OF_LINE_DELIMITER = " ";
    private static final String LINE_SEPARATOR = "-";
    private static final String END_OF_LINE = "\n";
    private static final String SPLIT_COLUMNS = "|";
    private static final String FIRST_COLUMN_HEADING = "№";
    private static final String SECOND_COLUMN_HEADING = "Email";
    private static final String THIRD_COLUMN_HEADING = "Role";
    private static final int LENGTH_SPACE_FIRST_COLUMN = 3;
    private static final int LENGTH_SPACE_SECOND_COLUMN = 30;
    private static final int LENGTH_SPACE_THIRD_COLUMN = 20;
    private static final int INDEX_ADDITION = 1;
    public static String getStringFromUsers(List<UserDto> usersDto) {
        log.debug("Method getStringFromUsers");
        log.trace("Parameter usersDto: {}", usersDto);
        String line = START_OF_LINE_DELIMITER + String.join(SYMBOL_FOR_JOIN_METHOD, Collections.nCopies(LINE_LENGTH, LINE_SEPARATOR)) +
                      END_OF_LINE;
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append(SPLIT_COLUMNS)
            .append(StringUtil.fillStringSpace(FIRST_COLUMN_HEADING, LENGTH_SPACE_FIRST_COLUMN));
        stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(SECOND_COLUMN_HEADING,
                                                                              LENGTH_SPACE_SECOND_COLUMN));
        stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(THIRD_COLUMN_HEADING,
                                                                              LENGTH_SPACE_THIRD_COLUMN))
            .append(SPLIT_COLUMNS + END_OF_LINE);
        stringBuilder.append(line);
        int bound = usersDto.size();
        IntStream.range(0, bound)
            .forEach(i -> {
                stringBuilder.append(SPLIT_COLUMNS)
                    .append(StringUtil.fillStringSpace(String.valueOf(i + INDEX_ADDITION), LENGTH_SPACE_FIRST_COLUMN));
                stringBuilder.append(SPLIT_COLUMNS)
                    .append(StringUtil.fillStringSpace(String.valueOf(usersDto.get(i).getEmail()),
                                                       LENGTH_SPACE_SECOND_COLUMN));
                stringBuilder.append(SPLIT_COLUMNS)
                    .append(StringUtil.fillStringSpace(String.valueOf(usersDto.get(i).getRole()),
                                                       LENGTH_SPACE_THIRD_COLUMN));
                stringBuilder.append(SPLIT_COLUMNS + END_OF_LINE);
            });
        stringBuilder.append(line);
        return stringBuilder.toString();
    }

}
