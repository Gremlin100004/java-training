package com.senla.carservice.ui.client;

public interface CarOfficeClient {
    String getFreePlacesMastersByDate(String date);

    String getNearestFreeDate();

    String exportEntities();

    String importEntities();

}
