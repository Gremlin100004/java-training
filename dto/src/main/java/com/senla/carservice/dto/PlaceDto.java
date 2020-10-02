package com.senla.carservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PlaceDto extends GeneralDto {
    private Integer number;
    private Boolean isBusy;
    private Boolean deleteStatus;
}