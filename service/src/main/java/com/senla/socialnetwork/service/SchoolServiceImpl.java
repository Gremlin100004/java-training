package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.SchoolMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    LocationDao locationDao;
    @Autowired
    SchoolDao schoolDao;

    @Override
    @Transactional
    public List<SchoolDto> getSchools(int firstResult, int maxResults) {
        log.debug("[getSchools]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return SchoolMapper.getSchoolDto(schoolDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public SchoolDto addSchool(SchoolDto schoolDto) {
        log.debug("[addSchool]");
        log.debug("[schoolDto: {}]", schoolDto);
        if (schoolDto == null) {
            throw new BusinessException("Error, null school");
        }
        return SchoolMapper.getSchoolDto(schoolDao.saveRecord(
            SchoolMapper.getSchool(schoolDto, schoolDao, locationDao)));
    }

    @Override
    @Transactional
    public void updateSchool(SchoolDto schoolDto) {
        log.debug("[updateSchool]");
        log.debug("[schoolDto: {}]", schoolDto);
        if (schoolDto == null) {
            throw new BusinessException("Error, null school");
        }
        schoolDao.saveRecord(SchoolMapper.getSchool(schoolDto, schoolDao, locationDao));
    }

    @Override
    @Transactional
    public void deleteSchool(Long schoolId) {
        log.debug("[deleteSchool]");
        log.debug("[schoolId: {}]", schoolId);
        if (schoolDao.findById(schoolId) == null) {
            throw new BusinessException("Error, there is no such school");
        }
        schoolDao.deleteRecord(schoolId);
    }

}