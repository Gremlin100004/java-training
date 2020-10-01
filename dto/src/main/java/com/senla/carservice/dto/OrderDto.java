package com.senla.carservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderDto extends GeneralDto {
    private Date creationTime;
    private Date executionStartTime;
    private Date leadTime;
    private String automaker;
    private String model;
    private String registrationNumber;
    private BigDecimal price;
    private String status = "WAIT";
    private boolean deleteStatus;
}