package com.senla.carservice.dto;

import com.senla.carservice.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto extends GeneralDto {
    private String email;
    private String password;
    private Role role;

}
