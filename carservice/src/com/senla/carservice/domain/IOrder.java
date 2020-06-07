package com.senla.carservice.domain;

import java.math.BigDecimal;
import java.util.Calendar;

public interface IOrder {
    IMaster[] getMasters();

    ICar getCar();

    String getStatus();

    IGarage getGarage();

    IPlace getPlace();

    Calendar getCreationTime();

    Calendar getExecutionStartTime();

    Calendar getLeadTime();

    BigDecimal getPrice();

    boolean isDeleteStatus();

    void setLeadTime(Calendar leadTime);

    void setGarage(Garage garage);

    void setPlace(Place place);

    void setCar(Car car);

    void setDeleteStatus(boolean deleteStatus);

    void setExecutionStartTime(Calendar executionStartTime);

    void setStatus(String status);

    void setPrice(BigDecimal price);
}