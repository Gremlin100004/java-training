package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.dto.UniversityForCreateDto;
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
    public List<UniversityDto> getUniversities(final int firstResult, final int maxResults) {
        log.debug("[getUniversity]");
        return UniversityMapper.getUniversityDto(universityDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public UniversityDto addUniversity(final UniversityForCreateDto universityDto) {
        log.debug("[addUniversity]");
        log.debug("[universityDto: {}]", universityDto);
        return UniversityMapper.getUniversityDto(universityDao.saveRecord(
            UniversityMapper.getNewUniversity(universityDto, locationDao)));
    }

    @Override
    @Transactional
    public void updateUniversity(final UniversityDto universityDto) {
        log.debug("[updateUniversity]");
        log.debug("[universityDto: {}]", universityDto);
        universityDao.updateRecord(UniversityMapper.getUniversity(universityDto, universityDao, locationDao));
    }

    @Override
    @Transactional
    public void deleteUniversity(final Long universityId) {
        log.debug("[deleteUniversity]");
        log.debug("[universityId: {}]", universityId);
        if (universityDao.findById(universityId) == null) {
            throw new BusinessException("Error, there is no such university");
        }
        universityDao.deleteRecord(universityId);
    }

}
