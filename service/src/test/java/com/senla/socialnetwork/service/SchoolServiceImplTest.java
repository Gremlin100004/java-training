package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.School;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.SchoolForCreateDto;
import com.senla.socialnetwork.service.config.LocationTestData;
import com.senla.socialnetwork.service.config.SchoolTestData;
import com.senla.socialnetwork.service.config.TestConfig;
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
public class SchoolServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    @Autowired
    SchoolService schoolService;
    @Autowired
    LocationDao locationDao;
    @Autowired
    SchoolDao schoolDao;

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
        Location location = LocationTestData.getTestLocation();
        Mockito.doReturn(school).when(schoolDao).saveRecord(ArgumentMatchers.any(School.class));
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());

        SchoolDto resultSchoolDto = schoolService.addSchool(schoolDto);
        Assertions.assertNotNull(resultSchoolDto);
        Mockito.verify(schoolDao, Mockito.never()).findById(SchoolTestData.getSchoolId());
        Mockito.verify(locationDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(1)).saveRecord(
            ArgumentMatchers.any(School.class));
        Mockito.reset(schoolDao);
        Mockito.reset(locationDao);
    }

    @Test
    void SchoolServiceImpl_updateSchool() {
        School school = SchoolTestData.getTestSchool();
        SchoolDto schoolDto = SchoolTestData.getTestSchoolDto();
        Location location = LocationTestData.getTestLocation();
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
        School location = SchoolTestData.getTestSchool();
        Mockito.doReturn(location).when(schoolDao).findById(SchoolTestData.getSchoolId());

        Assertions.assertDoesNotThrow(() -> schoolService.deleteSchool(SchoolTestData.getSchoolId()));
        Mockito.verify(schoolDao, Mockito.times(1)).deleteRecord(SchoolTestData.getSchoolId());
        Mockito.verify(schoolDao, Mockito.times(1)).findById(SchoolTestData.getSchoolId());
        Mockito.reset(schoolDao);
    }

    @Test
    void SchoolServiceImpl_deleteSchool_schoolDao_findById_nullObject() {
        Mockito.doReturn(null).when(schoolDao).findById(SchoolTestData.getSchoolId());

        Assertions.assertThrows(BusinessException.class, () -> schoolService.deleteSchool(SchoolTestData.getSchoolId()));
        Mockito.verify(schoolDao, Mockito.times(1)).findById(SchoolTestData.getSchoolId());
        Mockito.verify(schoolDao, Mockito.never()).deleteRecord(SchoolTestData.getSchoolId());
        Mockito.reset(schoolDao);
    }

}
