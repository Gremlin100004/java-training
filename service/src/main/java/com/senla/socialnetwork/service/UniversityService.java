package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.UniversityDto;

import java.util.List;

public interface UniversityService {
    List<UniversityDto> getUniversities(int firstResult, int maxResults);

    UniversityDto addUniversity(UniversityDto universityDto);

    void updateUniversity(UniversityDto universityDto);

    void deleteUniversity(Long universityId);

}
