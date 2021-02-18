package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.config.CommunityTestData;
import com.senla.socialnetwork.dao.config.TestConfig;
import com.senla.socialnetwork.dao.config.UserProfileTestData;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Slf4j
public class CommunityDaoTest {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 10;
    private static final String END_OF_TEST = "********* ****************************************";
    @Autowired
    private CommunityDao communityDao;

    @Test
    void CommunityDao_getCommunities() {
        log.info("********* Get communities *********");
        List<Community> resultCommunities = communityDao.getCommunities(FIRST_RESULT, MAX_RESULTS);
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_getCommunitiesByType() {
        log.info("********* Get communities by type *********");
        List<Community> resultCommunities = communityDao.getCommunitiesByType(
            CommunityType.SPORT, FIRST_RESULT, MAX_RESULTS);
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_getCommunitiesSortiedByNumberOfSubscribers() {
        log.info("********* Get communities sortied by number of subscribers *********");
        List<Community> resultCommunities = communityDao.getCommunitiesSortiedByNumberOfSubscribers(
            FIRST_RESULT, MAX_RESULTS);
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_getCommunitiesByEmail() {
        log.info("********* Get communities by email *********");
        List<Community> resultCommunities = communityDao.getCommunitiesByEmail(
            UserProfileTestData.getUserProfileEmail(), FIRST_RESULT, MAX_RESULTS);
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_getSubscribedCommunitiesByEmail() {
        log.info("********* Get subscribe communities by email *********");
        List<Community> resultCommunities = communityDao.getSubscribedCommunitiesByEmail(
            UserProfileTestData.getUserProfileEmail(), FIRST_RESULT, MAX_RESULTS);
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_findByIdAndEmail() {
        log.info("********* Find by id and email *********");
        Community community = communityDao.findByIdAndEmail(
            UserProfileTestData.getUserProfileEmail(), CommunityTestData.getCommunityId());
        log.info(community.toString());
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_saveRecord() {
        log.info("********* Save community *********");
        Community testCommunity = CommunityTestData.getTestCommunity();
        testCommunity.setId(null);
        communityDao.saveRecord(testCommunity);
        log.info(testCommunity.getId().toString());
        log.info("********* Delete record *********");
        communityDao.deleteRecord(testCommunity);
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_findById() {
        log.info("********* Find by id *********");
        Community community = communityDao.findById(CommunityTestData.getCommunityId());
        log.info(community.toString());
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_getAllRecords() {
        log.info("********* Get all records *********");
        List<Community> resultCommunities = communityDao.getAllRecords(FIRST_RESULT, MAX_RESULTS);
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_updateRecord() {
        log.info("********* Update record *********");
        communityDao.updateRecord(CommunityTestData.getTestCommunity());
        log.info(END_OF_TEST);
    }

}
