package com.senla.socialnetwork.dto;

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
@ApiModel(value = "User Profile For Create")
public class UserProfileForCreateDto {
    @ApiModelProperty(value = "User birthday",
        example = "2090-03-18")
    @Past
    private Date dateOfBirth;
    @ApiModelProperty(value = "User name",
        example = "Andrey")
    @NotNull
    private String name;
    @ApiModelProperty(value = "User name",
        example = "Litovka")
    @NotNull
    private String surname;
    @ApiModelProperty(value = "User mobile number",
        example = "+375(25)777-55-33")
    private String telephone_number;
    @ApiModelProperty(value = "User location")
    private LocationDto location;
    @ApiModelProperty(value = "Which user graduated from school")
    private SchoolDto school;
    @ApiModelProperty(value = "When the user finished school")
    private Integer schoolGraduationYear;
    @ApiModelProperty(value = "Which user graduated from university")
    private UniversityDto university;
    @ApiModelProperty(value = "When the user finished university")
    private Integer universityGraduationYear;

}
