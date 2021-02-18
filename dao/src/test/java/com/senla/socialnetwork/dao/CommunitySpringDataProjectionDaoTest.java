package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.config.CommunityTestData;
import com.senla.socialnetwork.dao.config.TestConfig;
import com.senla.socialnetwork.dao.config.UserProfileTestData;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import com.senla.socialnetwork.dto.CommunityProjectionDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Slf4j
public class CommunitySpringDataProjectionDaoTest {

    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 10;
    private static final String END_OF_TEST = "********* ****************************************";
    @Autowired
    private CommunitySpringDataProjectionNativeQueryDao communitySpringDataProjectionDao;

    @Test
    void CommunityDao_getCommunities() {
        log.info("********* Get communities *********");
        List<CommunityProjectionDto> resultCommunities = communitySpringDataProjectionDao.findByIsDeletedFalse(
                PageRequest.of(FIRST_RESULT, MAX_RESULTS));
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_getCommunitiesByType() {
        log.info("********* Get communities by type *********");
        List<CommunityProjectionDto> resultCommunities = communitySpringDataProjectionDao.getCommunitiesByType(
                CommunityType.SPORT.toString(), PageRequest.of(FIRST_RESULT, MAX_RESULTS));
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_getCommunitiesSortiedByNumberOfSubscribers() {
        log.info("********* Get communities sortied by number of subscribers *********");
        List<CommunityProjectionDto> resultCommunities = communitySpringDataProjectionDao.getCommunitiesSortiedByNumberOfSubscribers(
                PageRequest.of(FIRST_RESULT, MAX_RESULTS));
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_getCommunitiesByEmail() {
        log.info("********* Get communities by email *********");
        List<CommunityProjectionDto> resultCommunities = communitySpringDataProjectionDao.getCommunitiesByEmail(
                UserProfileTestData.getUserProfileEmail(), PageRequest.of(FIRST_RESULT, MAX_RESULTS));
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_getSubscribedCommunitiesByEmail() {
        log.info("********* Get communities by email *********");
        List<CommunityProjectionDto> resultCommunities = communitySpringDataProjectionDao.getSubscribedCommunitiesByEmail(
                UserProfileTestData.getUserProfileEmail(), PageRequest.of(FIRST_RESULT, MAX_RESULTS));
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_findByIdAndEmail() {
        log.info("********* Find by id and email *********");
        Optional<CommunityProjectionDto> community = communitySpringDataProjectionDao.findByIdAndEmail(
                CommunityTestData.getCommunityId(), UserProfileTestData.getUserProfileEmail());
        log.info(community.toString());
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_findById() {
        log.info("********* Find by id *********");
        Optional<CommunityProjectionDto> community = communitySpringDataProjectionDao.findById(CommunityTestData.getCommunityId());
        log.info(community.toString());
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_findAll() {
        log.info("********* Find all records *********");
        List<CommunityProjectionDto> resultCommunities = communitySpringDataProjectionDao
                .findAll(PageRequest.of(FIRST_RESULT, MAX_RESULTS));
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

}
