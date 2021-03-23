package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString(exclude = "password")
@NoArgsConstructor
@ApiModel(value = "User For Security")
public class UserForSecurityDto {
    @ApiModelProperty(value = "user email",
        example = "user1@test.com")
    @NotNull(message = "email must be specified")
    @Email(message = "incorrect email address")
    private String email;
    @ApiModelProperty(value = "user password",
        example = "test")
    @NotBlank(message = "password must be specified")
    @SuppressWarnings("checkstyle:MagicNumber")
    @Size(min = 4, message = "name must be specified")
    private String password;

}
