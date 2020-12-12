package com.senla.socialnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "User Profile")
public class UserProfileDto extends GeneralDto {
    @ApiModelProperty(value = "User registration date",
        example = "2020-07-11 10:00")
    @Past
    @NotNull(message = "date must be specified")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date registrationDate;
    @ApiModelProperty(value = "User birthday",
        example = "1990-07-28")
    @Past
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    @ApiModelProperty(value = "User name",
        example = "Petya")
    @NotNull(message = "name must be specified")
    private String name;
    @ApiModelProperty(value = "User name",
        example = "Buhmetovich")
    @NotNull(message = "surname must be specified")
    private String surname;
    @ApiModelProperty(value = "User mobile number",
        example = "+375(29)766-54-23")
    @Pattern(regexp = "^(\\+375\\(|80)(29|25|44|33)\\)(\\d{3})-(\\d{2})-(\\d{2})$",
        message = "must match +375(29)111-22-33")
    private String telephone_number;
    @ApiModelProperty(value = "User location")
    private LocationDto location;
    @ApiModelProperty(value = "Which user graduated from school")
    private SchoolDto school;
    @ApiModelProperty(value = "When the user finished school",
        example = "2007")
    private Integer schoolGraduationYear;
    @ApiModelProperty(value = "Which user graduated from university")
    private UniversityDto university;
    @ApiModelProperty(value = "When the user finished university",
        example = "2013")
    private Integer universityGraduationYear;

}
