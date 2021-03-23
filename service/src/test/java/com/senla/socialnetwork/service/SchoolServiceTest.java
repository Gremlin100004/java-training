package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.springdata.LocationSpringDataSpecificationDao;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.SchoolForCreateDto;
import com.senla.socialnetwork.model.Location;
import com.senla.socialnetwork.model.School;
import com.senla.socialnetwork.service.config.LocationTestData;
import com.senla.socialnetwork.service.config.SchoolTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = TestConfig.class)
public class SchoolServiceTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private LocationSpringDataSpecificationDao locationDao;
    @Autowired
    private SchoolDao schoolDao;

    @Test
    void SchoolServiceImpl_getSchools() {
        List<School> schools = SchoolTestData.getTestSchools();
        List<SchoolDto> schoolsDto = SchoolTestData.getTestSchoolsDto();
        Mockito.doReturn(schools).when(schoolDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<SchoolDto> resultSchoolsDto = schoolService.getSchools(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultSchoolsDto);
        Assertions.assertEquals(SchoolTestData.getRightNumberSchools(), resultSchoolsDto.size());
        Assertions.assertFalse(resultSchoolsDto.isEmpty());
        Assertions.assertEquals(resultSchoolsDto, schoolsDto);
        Mockito.verify(schoolDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(schoolDao);
    }

    @Test
    void SchoolServiceImpl_addSchool() {
        School school = SchoolTestData.getTestSchool();
        SchoolForCreateDto schoolDto = SchoolTestData.getTestSchoolForCreationDto();
        Optional<Location> location = Optional.of(LocationTestData.getTestLocation());
        Mockito.doReturn(school).when(schoolDao).save(ArgumentMatchers.any(School.class));
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());

        SchoolDto resultSchoolDto = schoolService.addSchool(schoolDto);
        Assertions.assertNotNull(resultSchoolDto);
        Mockito.verify(schoolDao, Mockito.never()).findById(SchoolTestData.getSchoolId());
        Mockito.verify(locationDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(1)).save(
            ArgumentMatchers.any(School.class));
        Mockito.reset(schoolDao);
        Mockito.reset(locationDao);
    }

    @Test
    void SchoolServiceImpl_updateSchool() {
        School school = SchoolTestData.getTestSchool();
        SchoolDto schoolDto = SchoolTestData.getTestSchoolDto();
        Optional<Location> location = Optional.of(LocationTestData.getTestLocation());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());

        Assertions.assertDoesNotThrow(() -> schoolService.updateSchool(schoolDto));
        Mockito.verify(schoolDao, Mockito.times(1)).findById(SchoolTestData.getSchoolId());
        Mockito.verify(locationDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(School.class));
        Mockito.reset(schoolDao);
        Mockito.reset(locationDao);
    }

    @Test
    void SchoolServiceImpl_deleteSchool() {
        School school = SchoolTestData.getTestSchool();
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());

        Assertions.assertDoesNotThrow(() -> schoolService.deleteSchool(SchoolTestData.getSchoolId()));
        Mockito.verify(schoolDao, Mockito.times(1)).deleteRecord(school);
        Mockito.verify(schoolDao, Mockito.times(1)).findById(SchoolTestData.getSchoolId());
        Mockito.reset(schoolDao);
    }

    @Test
    void SchoolServiceImpl_deleteSchool_schoolDao_findById_nullObject() {
        School school = SchoolTestData.getTestSchool();
        Mockito.doReturn(null).when(schoolDao).findById(SchoolTestData.getSchoolId());

        Assertions.assertThrows(BusinessException.class, () -> schoolService.deleteSchool(SchoolTestData.getSchoolId()));
        Mockito.verify(schoolDao, Mockito.times(1)).findById(SchoolTestData.getSchoolId());
        Mockito.verify(schoolDao, Mockito.never()).deleteRecord(school);
        Mockito.reset(schoolDao);
    }

}
