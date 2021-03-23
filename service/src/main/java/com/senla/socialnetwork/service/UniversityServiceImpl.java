package com.senla.socialnetwork.service;

import com.senla.socialnetwork.aspect.ServiceLog;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.springdata.LocationSpringDataSpecificationDao;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.dto.UniversityForCreateDto;
import com.senla.socialnetwork.model.University;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.UniversityMapper;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ServiceLog
@NoArgsConstructor
public class UniversityServiceImpl implements UniversityService {
    @Autowired
    private LocationSpringDataSpecificationDao locationDao;
    @Autowired
    private UniversityDao universityDao;

    @Override
    @Transactional
    public List<UniversityDto> getUniversities(final int firstResult, final int maxResults) {
        return UniversityMapper.getUniversityDto(universityDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public UniversityDto addUniversity(final UniversityForCreateDto universityDto) {
        return UniversityMapper.getUniversityDto(universityDao.save(
            UniversityMapper.getNewUniversity(universityDto, locationDao)));
    }

    @Override
    @Transactional
    public void updateUniversity(final UniversityDto universityDto) {
        universityDao.updateRecord(UniversityMapper.getUniversity(universityDto, universityDao, locationDao));
    }

    @Override
    @Transactional
    public void deleteUniversity(final Long universityId) {
        University university = universityDao.findById(universityId);
        if (university == null) {
            throw new BusinessException("Error, there is no such university");
        }
        universityDao.deleteRecord(university);
    }

}
