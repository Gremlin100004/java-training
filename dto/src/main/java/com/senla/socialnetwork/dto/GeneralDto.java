package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public class GeneralDto {
    @ApiModelProperty(value = "id",
        example = "1")
    @NotNull
    @SuppressWarnings("checkstyle:VisibilityModifier")
    protected Long id;

}
