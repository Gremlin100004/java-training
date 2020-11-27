package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "User For Security")
public class UserForSecurityDto {
    @ApiModelProperty(value = "user email",
        example = "user1@test.com")
    @NotNull
    @Email
    private String email;
    @ApiModelProperty(value = "user password",
        example = "test")
    @NotNull
    private String password;

}
