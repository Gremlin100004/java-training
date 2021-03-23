package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.enumaration.CommunityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class CommunityDaoTest extends AbstractDaoTest {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 0;
    private static final String TITTLE = "test";
    private static final String INFORMATION = "test";
    @Autowired
    private CommunityDao communityDao;

    @Test
    void CommunityDao_getCommunities() {
        List<Community> resultCommunities = communityDao.getCommunities(FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities.size(), testDataUtil.getCommunitiesNotDelete().size());
        Assertions.assertEquals(resultCommunities, testDataUtil.getCommunitiesNotDelete());
    }

    @Test
    void CommunityDao_getCommunitiesByType() {
        List<Community> communities = testDataUtil.getCommunitiesByType(CommunityType.SPORT);
        List<Community> resultCommunities = communityDao.getCommunitiesByType(
            CommunityType.SPORT, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities.size(), communities.size());
        Assertions.assertEquals(resultCommunities, communities);
    }

    @Test
    void CommunityDao_getCommunitiesSortiedByNumberOfSubscribers() {
        List<Community> resultCommunities = communityDao.getCommunitiesSortiedByNumberOfSubscribers(FIRST_RESULT,
                                                                                                    MAX_RESULT);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities.size(), testDataUtil.getCommunitiesNotDelete().size());
        Assertions.assertEquals(resultCommunities, testDataUtil.getCommunitiesNotDelete());
    }

    @Test
    void CommunityDao_getCommunitiesByEmail() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        List<Community> resultCommunities = communityDao.getCommunitiesByEmail(email, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities.size(), testDataUtil.getCommunitiesByEmail(email).size());
        Assertions.assertEquals(resultCommunities, testDataUtil.getCommunitiesByEmail(email));
    }

    @Test
    void CommunityDao_getSubscribedCommunitiesByEmail() {
        String email = testDataUtil.getUsers().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index).getEmail();

        List<Community> resultCommunities = communityDao.getSubscribedCommunitiesByEmail(
            email, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities.size(), testDataUtil.getSubscribedCommunitiesByEmail(email).size());
        Assertions.assertEquals(resultCommunities, testDataUtil.getSubscribedCommunitiesByEmail(email));
    }

    @Test
    void CommunityDao_findByIdAndEmail() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();
        Community community = testDataUtil.getCommunities().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index);

        Community resultCommunity = communityDao.findByIdAndEmail(email, community.getId());
        Assertions.assertNotNull(resultCommunity);
        Assertions.assertEquals(community, resultCommunity);
    }

    @Test
    void CommunityDao_saveRecord() {
        Community community = new Community();
        community.setIsDeleted(false);
        community.setInformation(INFORMATION);
        community.setTitle(TITTLE);
        community.setType(CommunityType.BUSINESS);
        community.setCreationDate(new Date());
        community.setAuthor(testDataUtil.getUserProfiles().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index));

        communityDao.save(community);
        Assertions.assertNotNull(community.getId());
    }

    @Test
    void CommunityDao_findById() {
        Community community = testDataUtil.getCommunities().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index);

        Community resultCommunity = communityDao.findById(community.getId());
        Assertions.assertNotNull(resultCommunity);
        Assertions.assertEquals(community, resultCommunity);
    }

    @Test
    void CommunityDao_getAllRecords() {
        List<Community> resultCommunities = communityDao.getAllRecords(FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities.size(), testDataUtil.getCommunities().size());
        Assertions.assertEquals(resultCommunities, testDataUtil.getCommunities());
    }

    @Test
    void CommunityDao_updateRecord() {
        Community community = testDataUtil.getCommunities().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);
        community.setTitle(TITTLE);

        communityDao.updateRecord(community);
        Community resultCommunity = communityDao.findById(community.getId());
        Assertions.assertNotNull(resultCommunity);
        Assertions.assertEquals(community, resultCommunity);
    }

    @Test
    void CommunityDao_deleteRecord() {
        Community community = testDataUtil.getCommunities().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        communityDao.deleteRecord(community);
        Community resultCommunity = communityDao.findById(community.getId());
        Assertions.assertNull(resultCommunity);
    }

}
