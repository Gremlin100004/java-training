package com.senla.carservice.dto;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Place;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderDto extends GeneralDto {
    private Date creationTime;
    private Date executionStartTime;
    private Date leadTime;
    private PlaceDto place;
    private String automaker;
    private String model;
    private String registrationNumber;
    private BigDecimal price;
    private String status;
    private boolean deleteStatus;
    private List<MasterDto> masters;
}