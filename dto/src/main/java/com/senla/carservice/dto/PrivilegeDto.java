package com.senla.carservice.dto;

import com.senla.carservice.domain.Role;
import com.senla.carservice.domain.enumaration.NamePrivilege;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PrivilegeDto extends GeneralDto {
    private NamePrivilege name;
    private Set<Role> roles;

}
