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
@ApiModel(value = "School For Create")
public class SchoolForCreateDto {
    @ApiModelProperty(value = "School name",
        example = "Gymnasium number 29")
    @NotNull(message = "name must be specified")
    private String name;
    @ApiModelProperty(value = "School location")
    @NotNull(message = "location must be specified")
    private LocationDto location;

}
