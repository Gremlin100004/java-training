package com.senla.carservice.csv;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private FileUtil() {
    }

    // НЕЙМИНГ!!!!
    // почему принимает СтрингБилдер на вход? Почему не просто стринг?
    // я не уверен, но мне кажется не очень оптимальным передавать в метод большую многострочную строку
    // лучше записывать в файл построчно (я могу ошибаться)
    public static String saveCsv(StringBuilder value, String path) {
        try (PrintStream printStream = new PrintStream(new FileOutputStream(path))) {
            printStream.print(value);
            return "save successfully";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    // НЕЙМИНГ!!!!
    // неоптимальное построение метода - в кэтче и вне трай одинаковые ретурны, это можно упростить
    public static List<String> getCsv(String path) {
        String line;
        List<String> csvLines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            while ((line = bufferedReader.readLine()) != null) {
                csvLines.add(line);
            }
            return csvLines;
        } catch (IOException e) {
            return null;
        }
    }
}