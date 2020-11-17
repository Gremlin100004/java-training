package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class CommunityServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    private static final int MAX_RESULTS = 0;
    private static final Long COMMUNITY_ID = 1L;
    private static final Long USER_PROFILE_ID = 1L;
    private static final Long POST_ID = 1L;
    private static final Long COMMUNITY_OTHER_ID = 2L;
    private static final Long USER_PROFILE_OTHER_ID = 2L;
    private static final Long POST_OTHER_ID = 2L;
    private static final Long RIGHT_NUMBER_COMMUNITIES = 2L;
    private static final Long RIGHT_NUMBER_POSTS = 2L;
    private static final Date USER_PROFILE_CREATION_DATE = new Date();
    private static final Date POST_CREATION_DATE = new Date();
    private static final String COMMUNITY_TITTLE = "Test";
    private static final String POST_TITTLE = "Test";
    private static final String EMAIL = "test@test";
    @Autowired
    CommunityService communityService;
    @Autowired
    CommunityDao communityDao;
    @Autowired
    PostDao postDao;
    @Autowired
    UserProfileDao userProfileDao;

    @Test
    void CommunityServiceImpl_getAllCommunities() {
        List<Community> communities = getTestCommunities();
        List<CommunityDto> communitiesDto = getTestCommunitiesDto();
        Mockito.doReturn(communities).when(communityDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<CommunityDto> resultCommunities = communityService.getAllCommunities(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertEquals(RIGHT_NUMBER_COMMUNITIES, resultCommunities.size());
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities, communitiesDto);
        Mockito.verify(communityDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_getCommunities() {
        List<Community> communities = getTestCommunities();
        List<CommunityDto> communitiesDto = getTestCommunitiesDto();
        Mockito.doReturn(communities).when(communityDao).getCommunities(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<CommunityDto> resultCommunities = communityService.getCommunities(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertEquals(RIGHT_NUMBER_COMMUNITIES, resultCommunities.size());
        Assertions.assertFalse(resultCommunities.get(0).isDeleted());
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities, communitiesDto);
        Mockito.verify(communityDao, Mockito.times(1)).getCommunities(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_getCommunitiesSortiedByNumberOfSubscribers() {
        List<Community> communities = getTestCommunities();
        List<CommunityDto> communitiesDto = getTestCommunitiesDto();
        Mockito.doReturn(communities).when(communityDao).getCommunitiesSortiedByNumberOfSubscribers(
            FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<CommunityDto> resultCommunities = communityService.getCommunitiesSortiedByNumberOfSubscribers(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertEquals(RIGHT_NUMBER_COMMUNITIES, resultCommunities.size());
        Assertions.assertFalse(resultCommunities.get(0).isDeleted());
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities, communitiesDto);
        Mockito.verify(communityDao, Mockito.times(
            1)).getCommunitiesSortiedByNumberOfSubscribers(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_getCommunitiesFilteredByType() {
        List<Community> communities = getTestCommunities();
        List<CommunityDto> communitiesDto = getTestCommunitiesDto();
        Mockito.doReturn(communities).when(communityDao).getCommunitiesByType(
            CommunityType.GENERAL, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<CommunityDto> resultCommunities = communityService.getCommunitiesFilteredByType(
            CommunityType.GENERAL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertEquals(RIGHT_NUMBER_COMMUNITIES, resultCommunities.size());
        Assertions.assertFalse(resultCommunities.get(0).isDeleted());
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities, communitiesDto);
        Mockito.verify(communityDao, Mockito.times(1)).getCommunitiesByType(
            CommunityType.GENERAL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_getOwnCommunities() {
        List<Community> communities = getTestCommunities();
        List<CommunityDto> communitiesDto = getTestCommunitiesDto();
        Mockito.doReturn(communities).when(communityDao).getOwnCommunitiesByEmail(EMAIL, FIRST_RESULT,
                                                                                  NORMAL_MAX_RESULTS);

        List<CommunityDto> resultCommunities = communityService.getOwnCommunities(EMAIL, FIRST_RESULT,
                                                                                  NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertEquals(RIGHT_NUMBER_COMMUNITIES, resultCommunities.size());
        Assertions.assertFalse(resultCommunities.get(0).isDeleted());
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities, communitiesDto);
        Mockito.verify(communityDao, Mockito.times(1)).getOwnCommunitiesByEmail(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_getSubscribedCommunities() {
        List<Community> communities = getTestCommunities();
        List<CommunityDto> communitiesDto = getTestCommunitiesDto();
        Mockito.doReturn(communities).when(communityDao).getSubscribedCommunitiesByEmail(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<CommunityDto> resultCommunities = communityService.getSubscribedCommunities(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertEquals(RIGHT_NUMBER_COMMUNITIES, resultCommunities.size());
        Assertions.assertFalse(resultCommunities.get(0).isDeleted());
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities, communitiesDto);
        Mockito.verify(communityDao, Mockito.times(1)).getSubscribedCommunitiesByEmail(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_subscribeToCommunity() {
        Community community = getTestCommunity();
        List<UserProfile> userProfiles = getTestUserProfiles();
        UserProfile userProfile = getTestUserProfile();
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(EMAIL);
        Mockito.doReturn(userProfiles).when(userProfileDao).getCommunityUsers(COMMUNITY_ID);

        Assertions.assertDoesNotThrow(() -> communityService.subscribeToCommunity(EMAIL, COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.times(1)).getCommunityUsers(COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void CommunityServiceImpl_subscribeToCommunity_communityDao_findByIdAndEmail_nullObject() {
        Mockito.doReturn(null).when(communityDao).findByIdAndEmail(EMAIL, COMMUNITY_ID);

        Assertions.assertThrows(BusinessException.class, () -> communityService.subscribeToCommunity(
            EMAIL, COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.never()).getCommunityUsers(COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(EMAIL);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_subscribeToCommunity_communityDao_findByIdAndEmail_objectDeleted() {
        Community community = getTestCommunity();
        community.setDeleted(true);
        Mockito.doReturn(null).when(communityDao).findByIdAndEmail(EMAIL, COMMUNITY_ID);

        Assertions.assertThrows(BusinessException.class, () -> communityService.subscribeToCommunity(
            EMAIL, COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.never()).getCommunityUsers(COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(EMAIL);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_unsubscribeFromCommunity() {
        Community community = getTestCommunity();
        List<UserProfile> userProfiles = getTestUserProfiles();
        UserProfile userProfile = getTestUserProfile();
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(EMAIL);
        Mockito.doReturn(userProfiles).when(userProfileDao).getCommunityUsers(COMMUNITY_ID);

        Assertions.assertDoesNotThrow(() -> communityService.unsubscribeFromCommunity(EMAIL, COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.times(1)).getCommunityUsers(COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void CommunityServiceImpl_unsubscribeFromCommunity_communityDao_findByIdAndEmail_nullObject() {
        Mockito.doReturn(null).when(communityDao).findByIdAndEmail(EMAIL, COMMUNITY_ID);

        Assertions.assertThrows(BusinessException.class, () -> communityService.unsubscribeFromCommunity(
            EMAIL, COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.never()).getCommunityUsers(COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(EMAIL);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_unsubscribeFromCommunity_communityDao_findByIdAndEmail_objectDeleted() {
        Community community = getTestCommunity();
        community.setDeleted(true);
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(EMAIL, COMMUNITY_ID);

        Assertions.assertThrows(BusinessException.class, () -> communityService.unsubscribeFromCommunity(
            EMAIL, COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.never()).getCommunityUsers(COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(EMAIL);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_unsubscribeFromCommunity_userProfileDao_getCommunityUsers_emptyList() {
        Community community = getTestCommunity();
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.doReturn(new ArrayList<>()).when(userProfileDao).getCommunityUsers(COMMUNITY_ID);

        Assertions.assertThrows(BusinessException.class, () -> communityService.unsubscribeFromCommunity(
            EMAIL, COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.times(1)).getCommunityUsers(COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(EMAIL);
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void CommunityServiceImpl_getCommunityPosts() {
        List<PostDto> postsDto = getTestPostsDto();
        List<Post> posts = getTestPosts();
        Mockito.doReturn(posts).when(postDao).getByCommunityId(COMMUNITY_ID, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PostDto> resultPostsDto = communityService.getCommunityPosts(COMMUNITY_ID, FIRST_RESULT,
                                                                          NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPostsDto);
        Assertions.assertEquals(RIGHT_NUMBER_POSTS, resultPostsDto.size());
        Assertions.assertFalse(resultPostsDto.get(0).isDeleted());
        Assertions.assertFalse(resultPostsDto.isEmpty());
        Assertions.assertEquals(resultPostsDto, postsDto);
        Mockito.verify(postDao, Mockito.times(1)).getByCommunityId(
            COMMUNITY_ID, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(postDao);
    }

    @Test
    void CommunityServiceImpl_addCommunity() {
        CommunityDto communityDto = getTestCommunityDto();
        communityDto.setId(null);
        Community community = getTestCommunity();
        UserProfile userProfile = getTestUserProfile();
        Mockito.doReturn(community).when(communityDao).saveRecord(ArgumentMatchers.any(Community.class));
        Mockito.doReturn(userProfile).when(userProfileDao).findById(USER_PROFILE_ID);

        CommunityDto resultCommunityDto = communityService.addCommunity(communityDto);
        Assertions.assertNotNull(resultCommunityDto);
        Mockito.verify(communityDao, Mockito.times(1)).saveRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(USER_PROFILE_ID);
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void CommunityServiceImpl_addCommunity_argument_null() {
        Assertions.assertThrows(BusinessException.class, () -> communityService.addCommunity(null));
        Mockito.verify(communityDao, Mockito.never()).saveRecord(ArgumentMatchers.any(Community.class));
    }

    @Test
    void CommunityServiceImpl_updateCommunity() {
        CommunityDto communityDto = getTestCommunityDto();
        Community community = getTestCommunity();
        UserProfile userProfile = getTestUserProfile();
        Mockito.doReturn(community).when(communityDao).findById(COMMUNITY_ID);
        Mockito.doReturn(userProfile).when(userProfileDao).findById(USER_PROFILE_ID);

        Assertions.assertDoesNotThrow(() -> communityService.updateCommunity(communityDto));
        Mockito.verify(communityDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(USER_PROFILE_ID);
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void CommunityServiceImpl_updateCommunity_argument_null() {
        Assertions.assertThrows(BusinessException.class, () -> communityService.updateCommunity(null));
        Mockito.verify(communityDao, Mockito.never()).updateRecord(ArgumentMatchers.any(Community.class));
    }

    @Test
    void CommunityServiceImpl_deleteCommunityByUser() {
        Community community = getTestCommunity();
        List<Post> posts = getTestPosts();
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.doReturn(posts).when(postDao).getByCommunityId(COMMUNITY_ID, FIRST_RESULT, MAX_RESULTS);

        Assertions.assertDoesNotThrow(() -> communityService.deleteCommunityByUser(EMAIL, COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.verify(postDao, Mockito.times(1)).getByCommunityId(
            COMMUNITY_ID, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(communityDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
    }

    @Test
    void CommunityServiceImpl_deleteCommunityByUser_communityDao_findByIdAndEmail_nullObject() {
        Mockito.doReturn(null).when(communityDao).findByIdAndEmail(EMAIL, COMMUNITY_ID);

        Assertions.assertThrows(BusinessException.class, () -> communityService.deleteCommunityByUser(
            EMAIL, COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.verify(postDao, Mockito.never()).getByCommunityId(COMMUNITY_ID, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(communityDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
    }

    @Test
    void CommunityServiceImpl_deleteCommunityByUser_communityDao_findByIdAndEmail_objectDeleted() {
        Community community = getTestCommunity();
        community.setDeleted(true);
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(EMAIL, COMMUNITY_ID);

        Assertions.assertThrows(BusinessException.class, () -> communityService.deleteCommunityByUser(
            EMAIL, COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.verify(postDao, Mockito.never()).getByCommunityId(COMMUNITY_ID, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(communityDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
    }

    @Test
    void CommunityServiceImpl_deleteCommunity() {
        Community community = getTestCommunity();
        Mockito.doReturn(community).when(communityDao).findById(COMMUNITY_ID);

        Assertions.assertDoesNotThrow(() -> communityService.deleteCommunity(COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findById(COMMUNITY_ID);
        Mockito.verify(communityDao, Mockito.times(1)).deleteRecord(COMMUNITY_ID);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_deleteCommunity_communityDao_findById_nullObject() {
        Mockito.doReturn(null).when(communityDao).findById(COMMUNITY_ID);

        Assertions.assertThrows(BusinessException.class, () -> communityService.deleteCommunity(COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findById(COMMUNITY_ID);
        Mockito.verify(communityDao, Mockito.never()).deleteRecord(COMMUNITY_ID);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_addPostToCommunity() {
        PostDto postDto = getTestPostDto();
        Post post = getTestPost();
        Community community = getTestCommunity();
        List<Post> posts = getTestPosts();
        UserProfile userProfile = getTestUserProfile();
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.doReturn(posts).when(postDao).getByCommunityId(COMMUNITY_ID, FIRST_RESULT, MAX_RESULTS);
        Mockito.doReturn(post).when(postDao).findById(POST_ID);
        Mockito.doReturn(community).when(communityDao).findById(COMMUNITY_ID);
        Mockito.doReturn(userProfile).when(userProfileDao).findById(USER_PROFILE_ID);

        Assertions.assertDoesNotThrow(() -> communityService.addPostToCommunity(EMAIL, postDto, COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.verify(postDao, Mockito.times(1)).getByCommunityId(
            COMMUNITY_ID, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(communityDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.verify(postDao, Mockito.times(1)).findById(POST_ID);
        Mockito.verify(communityDao, Mockito.times(1)).findById(COMMUNITY_ID);
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(USER_PROFILE_ID);
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void CommunityServiceImpl_addPostToCommunity_nullObject() {

        Assertions.assertThrows(BusinessException.class, () -> communityService.addPostToCommunity(
            EMAIL, null, COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.never()).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.verify(postDao, Mockito.never()).getByCommunityId(
            COMMUNITY_ID, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(communityDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.verify(postDao, Mockito.never()).findById(POST_ID);
        Mockito.verify(communityDao, Mockito.never()).findById(COMMUNITY_ID);
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
    }

    @Test
    void CommunityServiceImpl_addPostToCommunity_communityDao_findByIdAndEmail_nullObject() {
        PostDto postDto = getTestPostDto();
        Mockito.doReturn(null).when(communityDao).findByIdAndEmail(EMAIL, COMMUNITY_ID);

        Assertions.assertThrows(BusinessException.class, () -> communityService.addPostToCommunity(
            EMAIL, postDto, COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.verify(postDao, Mockito.never()).getByCommunityId(
            COMMUNITY_ID, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(communityDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.verify(postDao, Mockito.never()).findById(POST_ID);
        Mockito.verify(communityDao, Mockito.never()).findById(COMMUNITY_ID);
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
    }

    @Test
    void CommunityServiceImpl_addPostToCommunity_communityDao_findByIdAndEmail_objectDeleted() {
        PostDto postDto = getTestPostDto();
        Community community = getTestCommunity();
        community.setDeleted(true);
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(EMAIL, COMMUNITY_ID);

        Assertions.assertThrows(BusinessException.class, () -> communityService.addPostToCommunity(
            EMAIL, postDto, COMMUNITY_ID));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(EMAIL, COMMUNITY_ID);
        Mockito.verify(postDao, Mockito.never()).getByCommunityId(
            COMMUNITY_ID, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(communityDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.verify(postDao, Mockito.never()).findById(POST_ID);
        Mockito.verify(communityDao, Mockito.never()).findById(COMMUNITY_ID);
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
    }

    private Community getTestCommunity() {
        Community community = new Community();
        community.setId(COMMUNITY_ID);
        community.setAuthor(getTestUserProfile());
        community.setTittle(COMMUNITY_TITTLE);
        community.setAuthor(getTestUserProfile());
        return community;
    }

    private CommunityDto getTestCommunityDto() {
        CommunityDto communityDto = new CommunityDto();
        communityDto.setId(COMMUNITY_ID);
        communityDto.setAuthor(getTestUserProfileDto());
        communityDto.setTittle(COMMUNITY_TITTLE);
        communityDto.setAuthor(getTestUserProfileDto());
        return communityDto;
    }

    private List<Community> getTestCommunities() {
        Community communityOne = getTestCommunity();
        Community communityTwo = getTestCommunity();
        communityTwo.setId(COMMUNITY_OTHER_ID);
        return Arrays.asList(communityOne, communityTwo);
    }

    private List<CommunityDto> getTestCommunitiesDto() {
        CommunityDto communityDtoOne = getTestCommunityDto();
        CommunityDto communityDtoTwo = getTestCommunityDto();
        communityDtoTwo.setId(COMMUNITY_OTHER_ID);
        List<CommunityDto> communitiesDto = new ArrayList<>();
        communitiesDto.add(communityDtoOne);
        communitiesDto.add(communityDtoTwo);
        return communitiesDto;
    }

    private UserProfile getTestUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(USER_PROFILE_ID);
        userProfile.setRegistrationDate(USER_PROFILE_CREATION_DATE);
        return userProfile;
    }

    private UserProfileDto getTestUserProfileDto() {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId(USER_PROFILE_ID);
        userProfileDto.setRegistrationDate(USER_PROFILE_CREATION_DATE);
        return userProfileDto;
    }

    private List<UserProfile> getTestUserProfiles() {
        UserProfile userProfileOne = getTestUserProfile();
        UserProfile userProfileTwo = getTestUserProfile();
        userProfileTwo.setId(USER_PROFILE_OTHER_ID);
        List<UserProfile> userProfiles = new ArrayList<>();
        userProfiles.add(userProfileOne);
        userProfiles.add(userProfileTwo);
        return userProfiles;
    }

    private Post getTestPost() {
        Post post = new Post();
        post.setId(POST_ID);
        post.setTittle(POST_TITTLE);
        post.setCreationDate(POST_CREATION_DATE);
        post.setCommunity(getTestCommunity());
        return post;
    }

    private PostDto getTestPostDto() {
        PostDto postDto = new PostDto();
        postDto.setId(POST_ID);
        postDto.setTittle(POST_TITTLE);
        postDto.setCreationDate(POST_CREATION_DATE);
        postDto.setCommunity(getTestCommunityDto());
        return postDto;
    }

    private List<Post> getTestPosts() {
        Post postOne = getTestPost();
        Post postTwo = getTestPost();
        postTwo.setId(POST_OTHER_ID);
        List<Post> posts = new ArrayList<>();
        posts.add(postOne);
        posts.add(postTwo);
        return posts;
    }

    private List<PostDto> getTestPostsDto() {
        PostDto postDtoOne = getTestPostDto();
        PostDto postDtoTwo = getTestPostDto();
        postDtoTwo.setId(POST_OTHER_ID);
        List<PostDto> postsDto = new ArrayList<>();
        postsDto.add(postDtoOne);
        postsDto.add(postDtoTwo);
        return postsDto;
    }

}
