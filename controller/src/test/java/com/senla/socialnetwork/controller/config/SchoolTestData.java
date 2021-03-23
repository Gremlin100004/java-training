package com.senla.socialnetwork.controller.config;

import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.SchoolForCreateDto;
import com.senla.socialnetwork.model.School;

import java.util.ArrayList;
import java.util.List;

public class SchoolTestData {
    private static final Long SCHOOL_ID = 1L;
    private static final Long SCHOOLS_OTHER_ID = 2L;
    private static final Long RIGHT_NUMBER_SCHOOLS = 2L;
    private static final String SCHOOL_NAME = "Test";

    public static Long getSchoolId() {
        return SCHOOL_ID;
    }

    public static Long getRightNumberSchools() {
        return RIGHT_NUMBER_SCHOOLS;
    }

    public static School getTestSchool() {
        School school = new School();
        school.setId(SCHOOL_ID);
        school.setName(SCHOOL_NAME);
        school.setLocation(LocationTestData.getLocation());
        return school;
    }

    public static SchoolDto getSchoolDto() {
        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setId(SCHOOL_ID);
        schoolDto.setName(SCHOOL_NAME);
        schoolDto.setLocation(LocationTestData.getLocationDto());
        return schoolDto;
    }

    public static SchoolForCreateDto getSchoolForCreationDto() {
        SchoolForCreateDto schoolDto = new SchoolForCreateDto();
        schoolDto.setName(SCHOOL_NAME);
        schoolDto.setLocation(LocationTestData.getLocationDto());
        return schoolDto;
    }

    public static List<School> getTestSchools() {
        School schoolOne = getTestSchool();
        School schoolTwo = getTestSchool();
        schoolTwo.setId(SCHOOLS_OTHER_ID);
        List<School> schools = new ArrayList<>();
        schools.add(schoolOne);
        schools.add(schoolTwo);
        return schools;
    }

    public static List<SchoolDto> getSchoolsDto() {
        SchoolDto schoolDtoOne = getSchoolDto();
        SchoolDto schoolDtoTwo = getSchoolDto();
        schoolDtoTwo.setId(SCHOOLS_OTHER_ID);
        List<SchoolDto> schoolsDto = new ArrayList<>();
        schoolsDto.add(schoolDtoOne);
        schoolsDto.add(schoolDtoTwo);
        return schoolsDto;
    }

}
