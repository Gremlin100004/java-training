package com.senla.socialnetwork.service;

import com.senla.socialnetwork.aspect.ServiceLog;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.springdata.LocationSpringDataSpecificationDao;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.SchoolForCreateDto;
import com.senla.socialnetwork.model.School;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.SchoolMapper;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ServiceLog
@NoArgsConstructor
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    private LocationSpringDataSpecificationDao locationDao;
    @Autowired
    private SchoolDao schoolDao;

    @Override
    @Transactional
    public List<SchoolDto> getSchools(final int firstResult, final int maxResults) {
        return SchoolMapper.getSchoolDto(schoolDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public SchoolDto addSchool(final SchoolForCreateDto schoolDto) {
        return SchoolMapper.getSchoolDto(schoolDao.save(
            SchoolMapper.getNewSchool(schoolDto, locationDao)));
    }

    @Override
    @Transactional
    public void updateSchool(final SchoolDto schoolDto) {
        schoolDao.updateRecord(SchoolMapper.getSchool(schoolDto, schoolDao, locationDao));
    }

    @Override
    @Transactional
    public void deleteSchool(final Long schoolId) {
        School school = schoolDao.findById(schoolId);
        if (school == null) {
            throw new BusinessException("Error, there is no such school");
        }
        schoolDao.deleteRecord(school);
    }

}
