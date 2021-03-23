package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.School;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SchoolDaoTest extends AbstractDaoTest {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 0;
    private static final String NAME = "test";
    @Autowired
    private SchoolDao schoolDao;

    @Test
    void SchoolDao_getAllRecord() {
        List<School> resultSchools = schoolDao.getAllRecords(FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultSchools);
        Assertions.assertFalse(resultSchools.isEmpty());
        Assertions.assertEquals(resultSchools.size(), testDataUtil.getSchools().size());
        Assertions.assertEquals(resultSchools, testDataUtil.getSchools());
    }

    @Test
    void SchoolDao_findById() {
        School resultSchool = schoolDao.findById(testDataUtil.getSchools().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getId());
        Assertions.assertNotNull(resultSchool);
        Assertions.assertEquals(testDataUtil.getSchools().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index), resultSchool);
    }

    @Test
    void SchoolDao_findById_ErrorId() {
        School resultSchool = schoolDao.findById((long) ArrayIndex.TWENTY_EIGHTH_INDEX_OF_ARRAY.index);
        Assertions.assertNull(resultSchool);
    }

    @Test
    void SchoolDao_saveRecord() {
        School school = new School();
        school.setLocation(testDataUtil.getLocations().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index));
        school.setName(NAME);

        schoolDao.save(school);
        Assertions.assertNotNull(school.getId());
    }

    @Test
    void SchoolDao_updateRecord() {
        School school = testDataUtil.getSchools().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);
        school.setName(NAME);

        schoolDao.updateRecord(school);
        School resultSchool = schoolDao.findById(school.getId());
        Assertions.assertNotNull(resultSchool);
        Assertions.assertEquals(school, resultSchool);
    }

    @Test
    void SchoolDao_deleteRecord() {
        School school = testDataUtil.getSchools().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        schoolDao.deleteRecord(school);
        School resultSchool = schoolDao.findById(school.getId());
        Assertions.assertNull(resultSchool);
    }

}
