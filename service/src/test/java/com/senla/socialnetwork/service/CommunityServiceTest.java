package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.dto.CommunityForCreateDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.dto.PostForCreationDto;
import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.Post;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.model.enumaration.CommunityType;
import com.senla.socialnetwork.service.config.CommunityTestData;
import com.senla.socialnetwork.service.config.PostTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.config.UserProfileTestData;
import com.senla.socialnetwork.service.config.UserTestData;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = TestConfig.class)
public class CommunityServiceTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    private static final int MAX_RESULTS = 0;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private CommunityDao communityDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private UserProfileDao userProfileDao;

    @Test
    void CommunityServiceImpl_getAllCommunities() {
        List<Community> communities = CommunityTestData.getTestCommunities();
        List<CommunityDto> communitiesDto = CommunityTestData.getTestCommunitiesDto();
        Mockito.doReturn(communities).when(communityDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<CommunityDto> resultCommunities = communityService.getAllCommunities(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertEquals(CommunityTestData.getRightNumberCommunities(), resultCommunities.size());
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities, communitiesDto);
        Mockito.verify(communityDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_getCommunities() {
        List<Community> communities = CommunityTestData.getTestCommunities();
        List<CommunityDto> communitiesDto = CommunityTestData.getTestCommunitiesDto();
        Mockito.doReturn(communities).when(communityDao).getCommunities(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<CommunityDto> resultCommunities = communityService.getCommunities(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertEquals(CommunityTestData.getRightNumberCommunities(), resultCommunities.size());
        Assertions.assertFalse(resultCommunities.get(0).getDeleted());
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities, communitiesDto);
        Mockito.verify(communityDao, Mockito.times(1)).getCommunities(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_getCommunitiesSortiedByNumberOfSubscribers() {
        List<Community> communities = CommunityTestData.getTestCommunities();
        List<CommunityDto> communitiesDto = CommunityTestData.getTestCommunitiesDto();
        Mockito.doReturn(communities).when(communityDao).getCommunitiesSortiedByNumberOfSubscribers(
            FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<CommunityDto> resultCommunities = communityService.getCommunitiesSortiedByNumberOfSubscribers(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertEquals(CommunityTestData.getRightNumberCommunities(), resultCommunities.size());
        Assertions.assertFalse(resultCommunities.get(0).getDeleted());
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities, communitiesDto);
        Mockito.verify(communityDao, Mockito.times(
            1)).getCommunitiesSortiedByNumberOfSubscribers(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_getCommunitiesFilteredByType() {
        List<Community> communities = CommunityTestData.getTestCommunities();
        List<CommunityDto> communitiesDto = CommunityTestData.getTestCommunitiesDto();
        Mockito.doReturn(communities).when(communityDao).getCommunitiesByType(
            CommunityType.GENERAL, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<CommunityDto> resultCommunities = communityService.getCommunitiesFilteredByType(
            CommunityType.GENERAL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertEquals(CommunityTestData.getRightNumberCommunities(), resultCommunities.size());
        Assertions.assertFalse(resultCommunities.get(0).getDeleted());
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities, communitiesDto);
        Mockito.verify(communityDao, Mockito.times(1)).getCommunitiesByType(
            CommunityType.GENERAL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_getOwnCommunities() {
        List<Community> communities = CommunityTestData.getTestCommunities();
        List<CommunityDto> communitiesDto = CommunityTestData.getTestCommunitiesDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(communities).when(communityDao).getCommunitiesByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<CommunityDto> resultCommunities = communityService.getOwnCommunities(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertEquals(CommunityTestData.getRightNumberCommunities(), resultCommunities.size());
        Assertions.assertFalse(resultCommunities.get(0).getDeleted());
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities, communitiesDto);
        Mockito.verify(communityDao, Mockito.times(1)).getCommunitiesByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_getSubscribedCommunities() {
        List<Community> communities = CommunityTestData.getTestCommunities();
        List<CommunityDto> communitiesDto = CommunityTestData.getTestCommunitiesDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(communities).when(communityDao).getSubscribedCommunitiesByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        List<CommunityDto> resultCommunities = communityService.getSubscribedCommunities(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertEquals(CommunityTestData.getRightNumberCommunities(), resultCommunities.size());
        Assertions.assertFalse(resultCommunities.get(0).getDeleted());
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities, communitiesDto);
        Mockito.verify(communityDao, Mockito.times(1)).getSubscribedCommunitiesByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_subscribeToCommunity() {
        Community community = CommunityTestData.getTestCommunity();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        userProfile.setCommunitiesSubscribedTo(new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(community).when(communityDao).findById(CommunityTestData.getCommunityId());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());

        Assertions.assertDoesNotThrow(() -> communityService.subscribeToCommunity(CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findById(
            CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void CommunityServiceImpl_subscribeToCommunity_communityDao_findByIdAndEmail_nullObject() {
        Mockito.doReturn(null).when(communityDao).findById(CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.subscribeToCommunity(
            CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findById(
            CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(UserTestData.getEmail());
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_subscribeToCommunity_communityDao_findByIdAndEmail_objectDeleted() {
        Community community = CommunityTestData.getTestCommunity();
        community.setIsDeleted(true);
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(communityDao).findById(CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.subscribeToCommunity(
            CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findById(
            CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(UserTestData.getEmail());
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_subscribeToCommunity_getCommunityUsers_emptyList() {
        Community community = CommunityTestData.getTestCommunity();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        userProfile.setCommunitiesSubscribedTo(new ArrayList<>());
        userProfile.getCommunitiesSubscribedTo().add(community);
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(community).when(communityDao).findById(CommunityTestData.getCommunityId());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());

        Assertions.assertThrows(BusinessException.class, () -> communityService.subscribeToCommunity(
            CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findById(
            CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void CommunityServiceImpl_unsubscribeFromCommunity() {
        Community community = CommunityTestData.getTestCommunity();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUsersProfiles();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        userProfile.setCommunitiesSubscribedTo(new ArrayList<>());
        community.setSubscribers(userProfiles);
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(community).when(communityDao).findById(CommunityTestData.getCommunityId());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());

        Assertions.assertDoesNotThrow(() -> communityService.unsubscribeFromCommunity(
            CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findById(
            CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void CommunityServiceImpl_unsubscribeFromCommunity_communityDao_findByIdAndEmail_nullObject() {
        Mockito.doReturn(null).when(communityDao).findById(CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.unsubscribeFromCommunity(
            CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findById(
            CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(UserTestData.getEmail());
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_unsubscribeFromCommunity_communityDao_findByIdAndEmail_objectDeleted() {
        Community community = CommunityTestData.getTestCommunity();
        community.setIsDeleted(true);
        Mockito.doReturn(community).when(communityDao).findById(CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.unsubscribeFromCommunity(
            CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findById(
            CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(UserTestData.getEmail());
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_unsubscribeFromCommunity_userProfileDao_getCommunityUsers_emptyList() {
        Community community = CommunityTestData.getTestCommunity();
        community.setSubscribers(new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(community).when(communityDao).findById(CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.unsubscribeFromCommunity(
            CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findById(
            CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void CommunityServiceImpl_getCommunityPosts() {
        List<PostDto> postsDto = PostTestData.getTestPostsDto();
        List<Post> posts = PostTestData.getTestPosts();
        Mockito.doReturn(posts).when(postDao).getByCommunityId(
            CommunityTestData.getCommunityId(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PostDto> resultPostsDto = communityService.getCommunityPosts(
            CommunityTestData.getCommunityId(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPostsDto);
        Assertions.assertEquals(PostTestData.getRightNumberPosts(), resultPostsDto.size());
        Assertions.assertFalse(resultPostsDto.get(0).getDeleted());
        Assertions.assertFalse(resultPostsDto.isEmpty());
        Assertions.assertEquals(resultPostsDto, postsDto);
        Mockito.verify(postDao, Mockito.times(1)).getByCommunityId(
            CommunityTestData.getCommunityId(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(postDao);
    }

    @Test
    void CommunityServiceImpl_addCommunity() {
        CommunityForCreateDto communityDto = CommunityTestData.getTestCommunityForCreationDto();
        Community community = CommunityTestData.getTestCommunity();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(community).when(communityDao).save(ArgumentMatchers.any(Community.class));

        CommunityDto resultCommunityDto = communityService.addCommunity(communityDto);
        Assertions.assertNotNull(resultCommunityDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(communityDao, Mockito.times(1)).save(
            ArgumentMatchers.any(Community.class));
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void CommunityServiceImpl_updateCommunity() {
        CommunityDto communityDto = CommunityTestData.getTestCommunityDto();
        Community community = CommunityTestData.getTestCommunity();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());

        Assertions.assertDoesNotThrow(() -> communityService.updateCommunity(communityDto));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(communityDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_updateCommunity_communityDao_findByIdAndEmail_nullObject() {
        CommunityDto communityDto = CommunityTestData.getTestCommunityDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(communityDao).findById(CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.updateCommunity(communityDto));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(communityDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_deleteCommunityByUser() {
        Community community = CommunityTestData.getTestCommunity();
        List<Post> posts = PostTestData.getTestPosts();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.doReturn(posts).when(postDao).getByCommunityId(
            CommunityTestData.getCommunityId(), FIRST_RESULT, MAX_RESULTS);

        Assertions.assertDoesNotThrow(() -> communityService.deleteCommunityByUser(CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(postDao, Mockito.times(1)).getByCommunityId(
            CommunityTestData.getCommunityId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(communityDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
    }

    @Test
    void CommunityServiceImpl_deleteCommunityByUser_communityDao_findByIdAndEmail_nullObject() {
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.deleteCommunityByUser(
            CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(postDao, Mockito.never()).getByCommunityId(
            CommunityTestData.getCommunityId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(communityDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
    }

    @Test
    void CommunityServiceImpl_deleteCommunityByUser_communityDao_findByIdAndEmail_objectDeleted() {
        Community community = CommunityTestData.getTestCommunity();
        community.setIsDeleted(true);
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.deleteCommunityByUser(
            CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(postDao, Mockito.never()).getByCommunityId(
            CommunityTestData.getCommunityId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(communityDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
    }

    @Test
    void CommunityServiceImpl_deleteCommunity() {
        Community community = CommunityTestData.getTestCommunity();
        Mockito.doReturn(community).when(communityDao).findById(CommunityTestData.getCommunityId());

        Assertions.assertDoesNotThrow(() -> communityService.deleteCommunity(CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findById(CommunityTestData.getCommunityId());
        Mockito.verify(communityDao, Mockito.times(1)).deleteRecord(community);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_deleteCommunity_communityDao_findById_nullObject() {
        Community community = CommunityTestData.getTestCommunity();
        Mockito.doReturn(null).when(communityDao).findById(CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.deleteCommunity(
            CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findById(
            CommunityTestData.getCommunityId());
        Mockito.verify(communityDao, Mockito.never()).deleteRecord(community);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_addPostToCommunity() {
        PostForCreationDto postDto = PostTestData.getTestPostForCreationDto();
        Post post = PostTestData.getTestPost();
        Community community = CommunityTestData.getTestCommunity();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.doReturn(post).when(postDao).save(ArgumentMatchers.any(Post.class));

        Assertions.assertDoesNotThrow(() -> communityService.addPostToCommunity(
            postDto, CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(postDao, Mockito.times(1)).save(ArgumentMatchers.any(Post.class));
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
    }

    @Test
    void CommunityServiceImpl_addPostToCommunity_communityDao_findByIdAndEmail_nullObject() {
        PostForCreationDto postDto = PostTestData.getTestPostForCreationDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.addPostToCommunity(
            postDto, CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(postDao, Mockito.never()).getByCommunityId(
            CommunityTestData.getCommunityId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(postDao, Mockito.never()).save(ArgumentMatchers.any(Post.class));
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
    }

    @Test
    void CommunityServiceImpl_addPostToCommunity_communityDao_findByIdAndEmail_objectDeleted() {
        PostForCreationDto postDto = PostTestData.getTestPostForCreationDto();
        Community community = CommunityTestData.getTestCommunity();
        community.setIsDeleted(true);
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.addPostToCommunity(
            postDto, CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(postDao, Mockito.never()).getByCommunityId(
            CommunityTestData.getCommunityId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(postDao, Mockito.never()).save(ArgumentMatchers.any(Post.class));
        Mockito.verify(communityDao, Mockito.never()).findById(CommunityTestData.getCommunityId());
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
    }

}
