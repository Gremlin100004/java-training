package com.senla.carservice.service;

import java.util.Date;

public interface CarOfficeService {

    Date getNearestFreeDate();

    void importEntities();

    void exportEntities();

    void closeSessionFactory();
}