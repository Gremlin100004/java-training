package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Null;

@Getter
@Setter
@EqualsAndHashCode
public class GeneralDto {
    @ApiModelProperty(value = "id",
        example = "1")
    @Null
    protected Long id;

}
