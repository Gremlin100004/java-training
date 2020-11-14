package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.UniversityMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class UniversityServiceImpl implements UniversityService {
    @Autowired
    LocationDao locationDao;
    @Autowired
    UniversityDao universityDao;

    @Override
    @Transactional
    public List<UniversityDto> getUniversities(int firstResult, int maxResults) {
        log.debug("[getUniversity]");
        return UniversityMapper.getUniversityDto(universityDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public UniversityDto addUniversity(UniversityDto universityDto) {
        log.debug("[addUniversity]");
        log.debug("[universityDto: {}]", universityDto);
        if (universityDto == null) {
            throw new BusinessException("Error, null university");
        }
        return UniversityMapper.getUniversityDto(universityDao.saveRecord(
            UniversityMapper.getUniversity(universityDto, universityDao, locationDao)));
    }

    @Override
    @Transactional
    public void updateUniversity(UniversityDto universityDto) {
        log.debug("[updateUniversity]");
        log.debug("[universityDto: {}]", universityDto);
        if (universityDto == null) {
            throw new BusinessException("Error, null university");
        }
        universityDao.saveRecord(UniversityMapper.getUniversity(universityDto, universityDao, locationDao));
    }

    @Override
    @Transactional
    public void deleteUniversity(Long universityId) {
        log.debug("[deleteUniversity]");
        log.debug("[universityId: {}]", universityId);
        if (universityDao.findById(universityId) == null) {
            throw new BusinessException("Error, there is no such university");
        }
        universityDao.deleteRecord(universityId);
    }

}
