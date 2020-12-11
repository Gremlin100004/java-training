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
@ApiModel(value = "University")
public class UniversityDto extends GeneralDto {
    @ApiModelProperty(value = "University name",
        example = "Academy of Management under the President of the Republic of Belarus")
    @NotNull(message = "name must be specified")
    private String name;
    @ApiModelProperty(value = "University location")
    @NotNull(message = "location must be specified")
    private LocationDto location;

}
