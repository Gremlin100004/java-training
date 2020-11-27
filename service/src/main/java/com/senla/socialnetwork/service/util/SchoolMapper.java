package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.domain.School;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.SchoolForCreateDto;

import java.util.List;
import java.util.stream.Collectors;

public class SchoolMapper {

    public static SchoolDto getSchoolDto(final School school) {
        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setId(school.getId());
        schoolDto.setName(school.getName());
        schoolDto.setLocation(LocationMapper.getLocationDto(school.getLocation()));
        return schoolDto;
    }

    public static List<SchoolDto> getSchoolDto(final List<School> schools) {
        return schools.stream()
            .map(SchoolMapper::getSchoolDto)
            .collect(Collectors.toList());
    }

    public static School getSchool(final SchoolDto schoolDto, final SchoolDao schoolDao, final LocationDao locationDao) {
        School school = schoolDao.findById(schoolDto.getId());
        school.setName(schoolDto.getName());
        school.setLocation(LocationMapper.getLocation(schoolDto.getLocation(), locationDao));
        return school;
    }

    public static School getNewSchool(final SchoolForCreateDto schoolDto, final LocationDao locationDao) {
        School school = new School();
        school.setName(schoolDto.getName());
        school.setLocation(LocationMapper.getLocation(schoolDto.getLocation(), locationDao));
        return school;
    }

}
