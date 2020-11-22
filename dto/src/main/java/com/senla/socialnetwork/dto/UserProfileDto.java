package com.senla.socialnetwork.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "User Profile")
public class UserProfileDto extends GeneralDto {
    private Date registrationDate;
    private Date dateOfBirth;
    private String name;
    private String surname;
    private String telephone_number;
    private LocationDto location;
    private SchoolDto school;
    private Integer schoolGraduationYear;
    private UniversityDto university;
    private Integer universityGraduationYear;

}
