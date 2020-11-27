package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "Weather Condition")
public class WeatherConditionDto {
    @ApiModelProperty(value = "Weather condition at the user's location",
        example = "Clear sky, Few clouds, Scattered Clouds, Broken clouds, Shower rain, Rain, Thunderstorm, Snow, Mist")
    private String status;

}
