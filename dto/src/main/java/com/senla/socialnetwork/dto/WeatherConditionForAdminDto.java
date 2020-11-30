package com.senla.socialnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "Weather Condition For Admin")
public class WeatherConditionForAdminDto extends GeneralDto {
    @ApiModelProperty(value = "Weather condition at the user's location",
        example = "Clear sky, Few clouds, Scattered Clouds, Broken clouds, Shower rain, Rain, Thunderstorm, Snow, Mist")
    private String status;
    @ApiModelProperty(value = "Client's location")
    private LocationDto location;
    @ApiModelProperty(value = "Date of registration of weather condition",
        example = "2020-07-10 10:01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date registrationDate;

}
