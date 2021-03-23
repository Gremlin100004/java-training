package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.University;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UniversityDaoTest extends AbstractDaoTest {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 0;
    private static final String NAME = "test";
    @Autowired
    private UniversityDao universityDao;

    @Test
    void UniversityDao_getAllRecord() {
        List<University> resultUniversity = universityDao.getAllRecords(FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultUniversity);
        Assertions.assertFalse(resultUniversity.isEmpty());
        Assertions.assertEquals(resultUniversity.size(), testDataUtil.getUniversities().size());
        Assertions.assertEquals(resultUniversity, testDataUtil.getUniversities());
    }

    @Test
    void UniversityDao_findById() {
        University resultUniversity = universityDao.findById(testDataUtil.getUniversities().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getId());
        Assertions.assertNotNull(resultUniversity);
        Assertions.assertEquals(testDataUtil.getUniversities().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index), resultUniversity);
    }

    @Test
    void UniversityDao_findById_ErrorId() {
        University resultUniversity = universityDao.findById((long) ArrayIndex.TWENTY_EIGHTH_INDEX_OF_ARRAY.index);
        Assertions.assertNull(resultUniversity);
    }

    @Test
    void UniversityDao_saveRecord() {
        University university = new University();
        university.setLocation(testDataUtil.getLocations().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index));
        university.setName(NAME);

        universityDao.save(university);
        Assertions.assertNotNull(university.getId());
    }

    @Test
    void UniversityDao_updateRecord() {
        University university = testDataUtil.getUniversities().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);
        university.setName(NAME);

        universityDao.updateRecord(university);
        University resultUniversity = universityDao.findById(university.getId());
        Assertions.assertNotNull(resultUniversity);
        Assertions.assertEquals(university, resultUniversity);
    }

    @Test
    void UniversityDao_deleteRecord() {
        University university = testDataUtil.getUniversities().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        universityDao.deleteRecord(university);
        University resultUniversity = universityDao.findById(university.getId());
        Assertions.assertNull(resultUniversity);
    }

}
