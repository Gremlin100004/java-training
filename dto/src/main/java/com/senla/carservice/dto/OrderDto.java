package com.senla.carservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderDto extends GeneralDto {
    private String creationTime;
    private String executionStartTime;
    private String leadTime;
    private PlaceDto place;
    private String automaker;
    private String model;
    private String registrationNumber;
    private BigDecimal price;
    private String status;
    private boolean deleteStatus;
    private List<MasterDto> masters;

}
