package com.senla.socialnetwork.dto;

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
    private String role;

}
