package com.senla.socialnetwork.service.config;

import com.senla.socialnetwork.domain.University;
import com.senla.socialnetwork.dto.UniversityDto;

import java.util.ArrayList;
import java.util.List;

public class UniversityTestData {
    private static final Long UNIVERSITY_ID = 1L;
    private static final Long UNIVERSITY_OTHER_ID = 2L;
    private static final Long RIGHT_NUMBER_UNIVERSITIES = 2L;
    private static final String UNIVERSITY_NAME = "Test";

    public static Long getUniversityId() {
        return UNIVERSITY_ID;
    }

    public static Long getRightNumberUniversities() {
        return RIGHT_NUMBER_UNIVERSITIES;
    }

    public static University getTestUniversity() {
        University university = new University();
        university.setId(UNIVERSITY_ID);
        university.setName(UNIVERSITY_NAME);
        university.setLocation(LocationTestData.getTestLocation());
        return university;
    }

    public static UniversityDto getTestUniversityDto() {
        UniversityDto universityDto = new UniversityDto();
        universityDto.setId(UNIVERSITY_ID);
        universityDto.setName(UNIVERSITY_NAME);
        universityDto.setLocation(LocationTestData.getTestLocationDto());
        return universityDto;
    }

    public static List<University> getTestUniversities() {
        University universityOne = getTestUniversity();
        University universityTwo = getTestUniversity();
        universityTwo.setId(UNIVERSITY_OTHER_ID);
        List<University> universities = new ArrayList<>();
        universities.add(universityOne);
        universities.add(universityTwo);
        return universities;
    }

    public static List<UniversityDto> getTestUniversitiesDto() {
        UniversityDto universityDtoOne = getTestUniversityDto();
        UniversityDto universityDtoTwo = getTestUniversityDto();
        universityDtoTwo.setId(UNIVERSITY_OTHER_ID);
        List<UniversityDto> schoolsDto = new ArrayList<>();
        schoolsDto.add(universityDtoOne);
        schoolsDto.add(universityDtoTwo);
        return schoolsDto;
    }
    
}
