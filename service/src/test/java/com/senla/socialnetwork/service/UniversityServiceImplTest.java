package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.University;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.dto.UniversityForCreateDto;
import com.senla.socialnetwork.service.config.LocationTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.config.UniversityTestData;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class UniversityServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    @Autowired
    UniversityService universityService;
    @Autowired
    LocationDao locationDao;
    @Autowired
    UniversityDao universityDao;

    @Test
    void UniversityServiceImpl_getUniversities() {
        List<University> universities = UniversityTestData.getTestUniversities();
        List<UniversityDto> universitiesDto = UniversityTestData.getTestUniversitiesDto();
        Mockito.doReturn(universities).when(universityDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UniversityDto> resultSchoolsDto = universityService.getUniversities(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultSchoolsDto);
        Assertions.assertEquals(UniversityTestData.getRightNumberUniversities(), resultSchoolsDto.size());
        Assertions.assertFalse(resultSchoolsDto.isEmpty());
        Assertions.assertEquals(resultSchoolsDto, universitiesDto);
        Mockito.verify(universityDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(universityDao);
    }

    @Test
    void UniversityServiceImpl_addUniversity() {
        University school = UniversityTestData.getTestUniversity();
        UniversityForCreateDto schoolDto = UniversityTestData.getTestUniversityForCreationDto();
        Location location = LocationTestData.getTestLocation();
        Mockito.doReturn(school).when(universityDao).saveRecord(ArgumentMatchers.any(University.class));
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());

        UniversityDto resultUniversityDto = universityService.addUniversity(schoolDto);
        Assertions.assertNotNull(resultUniversityDto);
        Mockito.verify(universityDao, Mockito.never()).findById(UniversityTestData.getUniversityId());
        Mockito.verify(locationDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.verify(universityDao, Mockito.times(1)).saveRecord(
            ArgumentMatchers.any(University.class));
        Mockito.reset(universityDao);
        Mockito.reset(locationDao);
    }

    @Test
    void UniversityServiceImpl_updateUniversity() {
        University school = UniversityTestData.getTestUniversity();
        UniversityDto schoolDto = UniversityTestData.getTestUniversityDto();
        Location location = LocationTestData.getTestLocation();
        Mockito.doReturn(school).when(universityDao).findById(UniversityTestData.getUniversityId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());

        Assertions.assertDoesNotThrow(() -> universityService.updateUniversity(schoolDto));
        Mockito.verify(universityDao, Mockito.times(1)).findById(
            UniversityTestData.getUniversityId());
        Mockito.verify(locationDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.verify(universityDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(University.class));
        Mockito.reset(universityDao);
        Mockito.reset(locationDao);
    }

    @Test
    void UniversityServiceImpl_deleteUniversity() {
        University university = UniversityTestData.getTestUniversity();
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        Assertions.assertDoesNotThrow(() -> universityService.deleteUniversity(UniversityTestData.getUniversityId()));
        Mockito.verify(universityDao, Mockito.times(1)).findById(UniversityTestData.getUniversityId());
        Mockito.verify(universityDao, Mockito.times(1)).deleteRecord(university);
        Mockito.reset(universityDao);
    }

    @Test
    void UniversityServiceImpl_deleteUniversity_universityDao_findById_nullObject() {
        University university = UniversityTestData.getTestUniversity();
        Mockito.doReturn(null).when(universityDao).findById(UniversityTestData.getUniversityId());

        Assertions.assertThrows(BusinessException.class, () -> universityService.deleteUniversity(
            UniversityTestData.getUniversityId()));
        Mockito.verify(universityDao, Mockito.times(1)).findById(UniversityTestData.getUniversityId());
        Mockito.verify(universityDao, Mockito.never()).deleteRecord(university);
        Mockito.reset(universityDao);
    }

}
