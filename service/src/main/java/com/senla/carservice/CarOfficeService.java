package com.senla.carservice;

import java.util.Date;

public interface CarOfficeService {
    Date getNearestFreeDate();

    void importEntities();

    void exportEntities();
}