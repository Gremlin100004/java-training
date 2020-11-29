package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "User Profile For Create")
public class UserProfileForIdentificationDto extends GeneralDto {
    @ApiModelProperty(value = "User name",
        example = "Petya")
    @NotNull
    private String name;
    @ApiModelProperty(value = "User name",
        example = "Buhmetovich")
    @NotNull
    private String surname;

}
