package com.senla.socialnetwork.dto;

import com.senla.socialnetwork.model.enumaration.RoleName;
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
@ToString
@NoArgsConstructor
@ApiModel(value = "User For Admin")
public class UserForAdminDto extends GeneralDto {
    @ApiModelProperty(value = "user email",
        example = "example@test.com")
    @NotNull(message = "email must be specified")
    @Email(message = "incorrect email address")
    private String email;
    @ApiModelProperty(value = "user password",
        example = "example")
    @NotBlank(message = "password must be specified")
    private String password;
    @SuppressWarnings("checkstyle:MagicNumber")
    @Size(min = 4, message = "name must be specified")
    @ApiModelProperty(value = "User role")
    private RoleName role;

}
