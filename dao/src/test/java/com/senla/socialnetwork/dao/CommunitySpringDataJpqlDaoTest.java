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
import org.springframework.data.domain.Page;
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
public class CommunitySpringDataJpqlDaoTest {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 10;
    private static final String END_OF_TEST = "********* ****************************************";
    @Autowired
    private CommunitySpringDataJpqlDao communitySpringJpqlDataDao;

    @Test
    void CommunityDao_getCommunities() {
        log.info("********* Get communities *********");
        List<Community> resultCommunities = communitySpringJpqlDataDao.findByIsDeletedFalse(
            PageRequest.of(FIRST_RESULT, MAX_RESULTS));
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_getCommunitiesByType() {
        log.info("********* Get communities by type *********");
        List<Community> resultCommunities = communitySpringJpqlDataDao.getCommunitiesByType(
            CommunityType.SPORT, PageRequest.of(FIRST_RESULT, MAX_RESULTS));
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_getCommunitiesSortiedByNumberOfSubscribers() {
        log.info("********* Get communities sortied by number of subscribers *********");
        List<Community> resultCommunities = communitySpringJpqlDataDao.OrderByCountBySubscribers(
            PageRequest.of(FIRST_RESULT, MAX_RESULTS));
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_getCommunitiesByEmail() {
        log.info("********* Get communities by email *********");
        List<Community> resultCommunities = communitySpringJpqlDataDao.getCommunitiesByEmail(
            UserProfileTestData.getUserProfileEmail(), PageRequest.of(FIRST_RESULT, MAX_RESULTS));
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_getSubscribedCommunitiesByEmail() {
        log.info("********* Get subscribed communities by email *********");
        List<Community> resultCommunities = communitySpringJpqlDataDao.getSubscribedCommunitiesByEmail(
            UserProfileTestData.getUserProfileEmail(), PageRequest.of(FIRST_RESULT, MAX_RESULTS));
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_findByIdAndEmail() {
        log.info("********* Find by id and email *********");
        Optional<Community> community = communitySpringJpqlDataDao.findByIdAndEmail(
            CommunityTestData.getCommunityId(), UserProfileTestData.getUserProfileEmail());
        log.info(community.toString());
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_save_delete_record() {
        log.info("********* Save community *********");
        Community testCommunity = CommunityTestData.getTestCommunity();
        testCommunity.setId(null);
        communitySpringJpqlDataDao.save(testCommunity);
        log.info(testCommunity.getId().toString());
        log.info("********* Delete record *********");
        communitySpringJpqlDataDao.delete(testCommunity);
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_findById() {
        log.info("********* Find by id *********");
        Optional<Community> community = communitySpringJpqlDataDao.findById(CommunityTestData.getCommunityId());
        log.info(community.toString());
        log.info(END_OF_TEST);
    }

    @Test
    void CommunityDao_findAll() {
        log.info("********* Find all records *********");
        Page<Community> resultCommunities = communitySpringJpqlDataDao
            .findAll(PageRequest.of(FIRST_RESULT, MAX_RESULTS));
        resultCommunities.forEach(community -> log.info(community.toString()));
        log.info(END_OF_TEST);
    }

}
