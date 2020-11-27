package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.SchoolForCreateDto;

import java.util.List;

public interface SchoolService {
    List<SchoolDto> getSchools(int firstResult, int maxResults);

    SchoolDto addSchool(SchoolForCreateDto schoolDto);

    void updateSchool(SchoolDto schoolDto);

    void deleteSchool(Long schoolId);

}
