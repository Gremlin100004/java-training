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
@ApiModel(value = "Location For Create")
public class LocationForCreateDto {
    @ApiModelProperty(value = "Country",
        example = "Belarus")
    @NotNull
    private String country;
    @ApiModelProperty(value = "City",
        example = "Maladzyechna")
    @NotNull
    private String city;

}
