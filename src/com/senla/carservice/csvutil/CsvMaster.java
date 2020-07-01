package com.senla.carservice.csvutil;

import com.senla.carservice.domain.Master;

import com.senla.carservice.repository.MasterRepositoryImpl;

import java.util.Arrays;
import java.util.List;

public class CsvMaster {
    private static final String MASTER_PATH = "csv/masters.csv";
    private static final String COMMA = ",";

    private CsvMaster() {
    }

    // пусть лучше метод будет void, в случае неудачи бросает исключение
    // контроллер проверит: если исключений не было, то отдаст сообщение, прописанное в контроллере
    // "save successfully"
    // если неудача - проверит исключение и отдаст сообщение из исключения
    public static String exportMasters(List<Master> masters) {
        // если попробовать ипользовать стримы, то не придется проверять, последняя строка или нет
        String valueCsv;
        for (int i = 0; i < masters.size(); i++) {
            if (i == masters.size() - 1) {
                valueCsv = convertToCsv(masters.get(i), false);
            } else {
                valueCsv = convertToCsv(masters.get(i), true);
            }
            // не очень рационально создавать поток для файла для каждой его строчки
            // лучше записать в файл или все, или ничего
            FileUtil.saveCsv(valueCsv, MASTER_PATH, i != 0);
        }
        return "save successfully";
    }

    public static String importMasters() {
        List<String> csvLinesMaster = FileUtil.getCsv(MASTER_PATH);
        csvLinesMaster.forEach(line -> {
            Master master = getMasterFromCsv(line);
            // сервис вызывает  утилиту, утилита не вызывает сервис, а просто что-то возвращает
            MasterRepositoryImpl.getInstance().updateMaster(master);
        });
        return "Masters have been import successfully!";
    }

    private static Master getMasterFromCsv(String line) {
        List<String> values = Arrays.asList(line.split(COMMA));
        Master master = new Master();
        // на преобразования неплохо бы повесить валидацию, вдруг будет исключение
        master.setId(Long.valueOf(values.get(0)));
        master.setName(values.get(1));
        if (values.get(2).equals("null")) {
            master.setNumberOrder(null);
        } else {
            master.setNumberOrder(Integer.valueOf(values.get(2)));
        }
        return master;
    }

    private static String convertToCsv(Master master, boolean isLineFeed) {
        if (isLineFeed) {
            // лучше использовать константу разделитель, чтобы формат чтения и записи совпадал
            // константу потом вынести в проперти
            return String.format("%s,%s,%s\n", master.getId(), master.getName(), master.getNumberOrder());
        } else {
            return String.format("%s,%s,%s", master.getId(), master.getName(), master.getNumberOrder());
        }
    }
}