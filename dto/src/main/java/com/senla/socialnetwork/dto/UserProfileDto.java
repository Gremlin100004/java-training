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
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date registrationDate;
    @ApiModelProperty(value = "User birthday",
        example = "1990-07-28")
    @Past
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    @ApiModelProperty(value = "User name",
        example = "Petya")
    @NotNull
    private String name;
    @ApiModelProperty(value = "User name",
        example = "Buhmetovich")
    @NotNull
    private String surname;
    @ApiModelProperty(value = "User mobile number",
        example = "+375(29)766-54-23")
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
