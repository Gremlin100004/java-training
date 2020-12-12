package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "Location For Create")
public class LocationForCreateDto {
    @ApiModelProperty(value = "Country",
        example = "Belarus")
    @NotBlank(message = "country must be specified")
    private String country;
    @ApiModelProperty(value = "City",
        example = "Maladzyechna")
    @NotBlank(message = "city must be specified")
    private String city;

}
