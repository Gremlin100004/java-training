package com.senla.socialnetwork.dto;

import com.senla.socialnetwork.domain.enumaration.RoleName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "User For Admin")
public class UserForAdminDto extends GeneralDto {
    @ApiModelProperty(value = "user email",
        example = "example@test.com")
    @NotNull
    @Email
    private String email;
    @ApiModelProperty(value = "user password",
        example = "example")
    @NotNull
    private String password;
    @ApiModelProperty(value = "User role")
    @Null
    private RoleName role;

}
