package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.domain.School;
import com.senla.socialnetwork.dto.SchoolDto;

public class SchoolMapper {
    public static SchoolDto getSchoolDto(School school) {
        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setId(school.getId());
        schoolDto.setName(school.getName());
        schoolDto.setLocation(LocationMapper.getLocationDto(school.getLocation()));
        return schoolDto;
    }

    public static School getSchool(SchoolDto schoolDto, SchoolDao schoolDao, LocationDao locationDao) {
        School school;
        if (schoolDto.getId() == null) {
            school = new School();
        } else {
            school = schoolDao.findById(schoolDto.getId());
        }
        school.setName(schoolDto.getName());
        school.setLocation(LocationMapper.getLocation(schoolDto.getLocation(), locationDao));
        return school;
    }

}
