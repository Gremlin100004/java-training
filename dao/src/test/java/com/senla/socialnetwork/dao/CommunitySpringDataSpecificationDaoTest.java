package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.dao.springdata.CommunitySpecification;
import com.senla.socialnetwork.dao.springdata.CommunitySpringDataSpecificationDao;
import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.enumaration.CommunityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CommunitySpringDataSpecificationDaoTest extends AbstractDaoTest {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 20;
    private static final String TITTLE = "test";
    private static final String INFORMATION = "test";
    @Autowired
    private CommunitySpringDataSpecificationDao communityDao;

    @Test
    void CommunityDao_findAll_communityIsDeleted() {
        Page<Community> resultCommunities = communityDao.findAll(
            CommunitySpecification.communityIsDeleted(false), PageRequest.of(FIRST_RESULT, MAX_RESULT));
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities.getContent().size(), testDataUtil.getCommunitiesNotDelete().size());
        Assertions.assertEquals(resultCommunities.toList(), testDataUtil.getCommunitiesNotDelete());
    }

    @Test
    void CommunityDao_findAll_communityBelongToType() {
        List<Community> communities = testDataUtil.getCommunitiesByType(CommunityType.SPORT);
        Page<Community> resultCommunities = communityDao.findAll(CommunitySpecification.communityBelongToType(
            CommunityType.SPORT), PageRequest.of(FIRST_RESULT, MAX_RESULT));
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities.getContent().size(), communities.size());
        Assertions.assertEquals(resultCommunities.getContent(), communities);
    }

    @Test
    void CommunityDao_getCommunitiesSortiedByNumberOfSubscribers() {
        List<Community> resultCommunities = communityDao.getCommunitiesSortiedByNumberOfSubscribers(
                FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities.size(), testDataUtil.getCommunitiesNotDelete().size());
        Assertions.assertTrue(resultCommunities.contains(testDataUtil.getCommunitiesNotDelete().get(
            ArrayIndex.FIRST_INDEX_OF_ARRAY.index)));
        Assertions.assertTrue(resultCommunities.contains(testDataUtil.getCommunitiesNotDelete().get(
            ArrayIndex.SECOND_INDEX_OF_ARRAY.index)));
        Assertions.assertTrue(resultCommunities.contains(testDataUtil.getCommunitiesNotDelete().get(
            ArrayIndex.THIRD_INDEX_OF_ARRAY.index)));
    }

    @Test
    void CommunityDao_getCommunitiesByEmail() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        Page<Community> resultCommunities = communityDao.findAll(
            CommunitySpecification.emailLike(email), PageRequest.of(FIRST_RESULT, MAX_RESULT));
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities.getContent().size(), testDataUtil.getCommunitiesByEmail(email).size());
        Assertions.assertEquals(resultCommunities.getContent(), testDataUtil.getCommunitiesByEmail(email));
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

        Optional<Community> resultCommunity = communityDao.findOne(CommunitySpecification.emailAndIdLike(
            community.getId(), email));
        Assertions.assertTrue(resultCommunity.isPresent());
        Assertions.assertNotNull(resultCommunity.get());
        Assertions.assertEquals(community, resultCommunity.get());
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

        Optional<Community> resultCommunity = communityDao.findById(community.getId());
        Assertions.assertTrue(resultCommunity.isPresent());
        Assertions.assertEquals(community, resultCommunity.get());
    }

    @Test
    void CommunityDao_getAllRecords() {
        List<Community> resultCommunities = communityDao.findAll(PageRequest.of(FIRST_RESULT, MAX_RESULT)).getContent();
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities.size(), testDataUtil.getCommunities().size());
        Assertions.assertEquals(resultCommunities, testDataUtil.getCommunities());
    }

    @Test
    void CommunityDao_updateRecord() {
        Community community = testDataUtil.getCommunities().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);
        community.setTitle(TITTLE);

        communityDao.save(community);
        Optional<Community> resultCommunity = communityDao.findById(community.getId());
        Assertions.assertTrue(resultCommunity.isPresent());
        Assertions.assertEquals(community, resultCommunity.get());
    }

    @Test
    void CommunityDao_deleteRecord() {
        Community community = testDataUtil.getCommunities().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        communityDao.delete(community);
        Optional<Community> resultCommunity = communityDao.findById(community.getId());
        Assertions.assertFalse(resultCommunity.isPresent());
    }

}
