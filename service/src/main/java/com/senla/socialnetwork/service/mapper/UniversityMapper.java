package com.senla.socialnetwork.service.mapper;

import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.springdata.LocationSpringDataSpecificationDao;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.dto.UniversityForCreateDto;
import com.senla.socialnetwork.model.University;

import java.util.List;
import java.util.stream.Collectors;

public final class UniversityMapper {
    private UniversityMapper() {
    }

    public static UniversityDto getUniversityDto(final University university) {
        UniversityDto universityDto = new UniversityDto();
        universityDto.setId(university.getId());
        universityDto.setName(university.getName());
        universityDto.setLocation(LocationMapper.getLocationDto(university.getLocation()));
        return universityDto;
    }

    public static List<UniversityDto> getUniversityDto(final List<University> universities) {
        return universities.stream()
            .map(UniversityMapper::getUniversityDto)
            .collect(Collectors.toList());
    }

    public static University getUniversity(final UniversityDto universityDto,
                                           final UniversityDao universityDao,
                                           final LocationSpringDataSpecificationDao locationDao) {
        University university = universityDao.findById(universityDto.getId());
        university.setName(universityDto.getName());
        university.setLocation(LocationMapper.getLocation(universityDto.getLocation(), locationDao));
        return university;
    }

    public static University getNewUniversity(final UniversityForCreateDto universityDto,
                                              final LocationSpringDataSpecificationDao locationDao) {
        University university = new University();
        university.setName(universityDto.getName());
        university.setLocation(LocationMapper.getLocation(universityDto.getLocation(), locationDao));
        return university;
    }

}
