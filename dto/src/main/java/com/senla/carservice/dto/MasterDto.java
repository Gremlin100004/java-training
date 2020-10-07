package com.senla.carservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MasterDto extends GeneralDto {
    private String name;
    private Integer numberOrders;
    private Boolean deleteStatus;

}
