package com.senla.carservice.dto;

import com.senla.carservice.domain.enumaration.NameRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RoleDto extends GeneralDto {
    private NameRole name;

}
