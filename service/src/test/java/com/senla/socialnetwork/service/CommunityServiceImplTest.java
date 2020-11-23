package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.domain.School;
import com.senla.socialnetwork.domain.University;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.service.config.CommunityTestData;
import com.senla.socialnetwork.service.config.LocationTestData;
import com.senla.socialnetwork.service.config.PostTestData;
import com.senla.socialnetwork.service.config.SchoolTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.config.UniversityTestData;
import com.senla.socialnetwork.service.config.UserProfileTestData;
import com.senla.socialnetwork.service.config.UserTestData;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class CommunityServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    private static final int MAX_RESULTS = 0;
    @Autowired
    CommunityService communityService;
    @Autowired
    CommunityDao communityDao;
    @Autowired
    PostDao postDao;
    @Autowired
    UserProfileDao userProfileDao;
    @Autowired
    LocationDao locationDao;
    @Autowired
    SchoolDao schoolDao;
    @Autowired
    UniversityDao universityDao;
    @Autowired
    private HttpServletRequest request;
    @Value("${com.senla.socialnetwork.JwtUtil.secret-key:qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq}")
    private String secretKey;

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
        Assertions.assertFalse(resultCommunities.get(0).isDeleted());
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
        Assertions.assertFalse(resultCommunities.get(0).isDeleted());
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
        Assertions.assertFalse(resultCommunities.get(0).isDeleted());
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
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(communities).when(communityDao).getOwnCommunitiesByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<CommunityDto> resultCommunities = communityService.getOwnCommunities(
            request, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertEquals(CommunityTestData.getRightNumberCommunities(), resultCommunities.size());
        Assertions.assertFalse(resultCommunities.get(0).isDeleted());
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities, communitiesDto);
        Mockito.verify(communityDao, Mockito.times(1)).getOwnCommunitiesByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_getSubscribedCommunities() {
        List<Community> communities = CommunityTestData.getTestCommunities();
        List<CommunityDto> communitiesDto = CommunityTestData.getTestCommunitiesDto();
        Mockito.doReturn(communities).when(communityDao).getSubscribedCommunitiesByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);

        List<CommunityDto> resultCommunities = communityService.getSubscribedCommunities(
            request, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultCommunities);
        Assertions.assertEquals(CommunityTestData.getRightNumberCommunities(), resultCommunities.size());
        Assertions.assertFalse(resultCommunities.get(0).isDeleted());
        Assertions.assertFalse(resultCommunities.isEmpty());
        Assertions.assertEquals(resultCommunities, communitiesDto);
        Mockito.verify(communityDao, Mockito.times(1)).getSubscribedCommunitiesByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_subscribeToCommunity() {
        Community community = CommunityTestData.getTestCommunity();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(userProfiles).when(userProfileDao).getCommunityUsers(CommunityTestData.getCommunityId());

        Assertions.assertDoesNotThrow(() -> communityService.subscribeToCommunity(
            request, CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(1)).getCommunityUsers(
            CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void CommunityServiceImpl_subscribeToCommunity_communityDao_findByIdAndEmail_nullObject() {
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(null).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.subscribeToCommunity(
            request, CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.never()).getCommunityUsers(CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(UserTestData.getEmail());
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_subscribeToCommunity_communityDao_findByIdAndEmail_objectDeleted() {
        Community community = CommunityTestData.getTestCommunity();
        community.setDeleted(true);
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(null).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.subscribeToCommunity(
            request, CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.never()).getCommunityUsers(CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(UserTestData.getEmail());
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_unsubscribeFromCommunity() {
        Community community = CommunityTestData.getTestCommunity();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(userProfiles).when(userProfileDao).getCommunityUsers(CommunityTestData.getCommunityId());

        Assertions.assertDoesNotThrow(() -> communityService.unsubscribeFromCommunity(
            request, CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(1)).getCommunityUsers(
            CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void CommunityServiceImpl_unsubscribeFromCommunity_communityDao_findByIdAndEmail_nullObject() {
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(null).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.unsubscribeFromCommunity(
            request, CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.never()).getCommunityUsers(CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(UserTestData.getEmail());
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_unsubscribeFromCommunity_communityDao_findByIdAndEmail_objectDeleted() {
        Community community = CommunityTestData.getTestCommunity();
        community.setDeleted(true);
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.unsubscribeFromCommunity(
            request, CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.never()).getCommunityUsers(CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(UserTestData.getEmail());
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_unsubscribeFromCommunity_userProfileDao_getCommunityUsers_emptyList() {
        Community community = CommunityTestData.getTestCommunity();
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.doReturn(new ArrayList<>()).when(userProfileDao).getCommunityUsers(CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.unsubscribeFromCommunity(
            request, CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(1)).getCommunityUsers(
            CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(UserTestData.getEmail());
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
        Assertions.assertFalse(resultPostsDto.get(0).isDeleted());
        Assertions.assertFalse(resultPostsDto.isEmpty());
        Assertions.assertEquals(resultPostsDto, postsDto);
        Mockito.verify(postDao, Mockito.times(1)).getByCommunityId(
            CommunityTestData.getCommunityId(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(postDao);
    }

    @Test
    void CommunityServiceImpl_addCommunity() {
        CommunityDto communityDto = CommunityTestData.getTestCommunityDto();
        communityDto.setId(null);
        Community community = CommunityTestData.getTestCommunity();
        Location location = LocationTestData.getTestLocation();
        School school = SchoolTestData.getTestSchool();
        University university = UniversityTestData.getTestUniversity();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Mockito.doReturn(community).when(communityDao).saveRecord(ArgumentMatchers.any(Community.class));
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        CommunityDto resultCommunityDto = communityService.addCommunity(communityDto);
        Assertions.assertNotNull(resultCommunityDto);
        Mockito.verify(communityDao, Mockito.times(1)).saveRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(
            UserProfileTestData.getUserProfileId());
        Mockito.verify(locationDao, Mockito.times(3)).findById(
            LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(1)).findById(
            SchoolTestData.getSchoolId());
        Mockito.verify(universityDao, Mockito.times(1)).findById(
            UniversityTestData.getUniversityId());
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
        Mockito.reset(universityDao);
    }

    @Test
    void CommunityServiceImpl_updateCommunity() {
        CommunityDto communityDto = CommunityTestData.getTestCommunityDto();
        Community community = CommunityTestData.getTestCommunity();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Location location = LocationTestData.getTestLocation();
        School school = SchoolTestData.getTestSchool();
        University university = UniversityTestData.getTestUniversity();
        Mockito.doReturn(community).when(communityDao).findById(CommunityTestData.getCommunityId());
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        Assertions.assertDoesNotThrow(() -> communityService.updateCommunity(communityDto));
        Mockito.verify(communityDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(
            UserProfileTestData.getUserProfileId());
        Mockito.verify(locationDao, Mockito.times(3)).findById(
            LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(1)).findById(
            SchoolTestData.getSchoolId());
        Mockito.verify(universityDao, Mockito.times(1)).findById(
            UniversityTestData.getUniversityId());
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
        Mockito.reset(universityDao);
    }

    @Test
    void CommunityServiceImpl_deleteCommunityByUser() {
        Community community = CommunityTestData.getTestCommunity();
        List<Post> posts = PostTestData.getTestPosts();
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.doReturn(posts).when(postDao).getByCommunityId(
            CommunityTestData.getCommunityId(), FIRST_RESULT, MAX_RESULTS);

        Assertions.assertDoesNotThrow(() -> communityService.deleteCommunityByUser(
            request, CommunityTestData.getCommunityId()));
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
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(null).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.deleteCommunityByUser(
            request, CommunityTestData.getCommunityId()));
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
        community.setDeleted(true);
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.deleteCommunityByUser(
            request, CommunityTestData.getCommunityId()));
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
        Mockito.verify(communityDao, Mockito.times(1)).deleteRecord(
            CommunityTestData.getCommunityId());
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_deleteCommunity_communityDao_findById_nullObject() {
        Mockito.doReturn(null).when(communityDao).findById(CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.deleteCommunity(
            CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findById(
            CommunityTestData.getCommunityId());
        Mockito.verify(communityDao, Mockito.never()).deleteRecord(CommunityTestData.getCommunityId());
        Mockito.reset(communityDao);
    }

    @Test
    void CommunityServiceImpl_addPostToCommunity() {
        PostDto postDto = PostTestData.getTestPostDto();
        Post post = PostTestData.getTestPost();
        Community community = CommunityTestData.getTestCommunity();
        List<Post> posts = PostTestData.getTestPosts();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Location location = LocationTestData.getTestLocation();
        School school = SchoolTestData.getTestSchool();
        University university = UniversityTestData.getTestUniversity();
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.doReturn(posts).when(postDao).getByCommunityId(
            CommunityTestData.getCommunityId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.doReturn(post).when(postDao).findById(PostTestData.getPostId());
        Mockito.doReturn(community).when(communityDao).findById(CommunityTestData.getCommunityId());
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        Assertions.assertDoesNotThrow(() -> communityService.addPostToCommunity(
            request, postDto, CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(postDao, Mockito.times(1)).getByCommunityId(
            CommunityTestData.getCommunityId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(communityDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.verify(postDao, Mockito.times(1)).findById(PostTestData.getPostId());
        Mockito.verify(communityDao, Mockito.times(1)).findById(CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(UserProfileTestData.getUserProfileId());
        Mockito.verify(locationDao, Mockito.times(3)).findById(
            LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(1)).findById(
            SchoolTestData.getSchoolId());
        Mockito.verify(universityDao, Mockito.times(1)).findById(
            UniversityTestData.getUniversityId());
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
        Mockito.reset(universityDao);
    }

    @Test
    void CommunityServiceImpl_addPostToCommunity_communityDao_findByIdAndEmail_nullObject() {
        PostDto postDto = PostTestData.getTestPostDto();
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(null).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.addPostToCommunity(
            request, postDto, CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(postDao, Mockito.never()).getByCommunityId(
            CommunityTestData.getCommunityId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(communityDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.verify(postDao, Mockito.never()).findById(PostTestData.getPostId());
        Mockito.verify(communityDao, Mockito.never()).findById(CommunityTestData.getCommunityId());
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
    }

    @Test
    void CommunityServiceImpl_addPostToCommunity_communityDao_findByIdAndEmail_objectDeleted() {
        PostDto postDto = PostTestData.getTestPostDto();
        Community community = CommunityTestData.getTestCommunity();
        community.setDeleted(true);
        Mockito.doReturn(UserTestData.getAuthorizationHeader(secretKey)).when(request).getHeader(
            HttpHeaders.AUTHORIZATION);
        Mockito.doReturn(community).when(communityDao).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());

        Assertions.assertThrows(BusinessException.class, () -> communityService.addPostToCommunity(
            request, postDto, CommunityTestData.getCommunityId()));
        Mockito.verify(communityDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), CommunityTestData.getCommunityId());
        Mockito.verify(postDao, Mockito.never()).getByCommunityId(
            CommunityTestData.getCommunityId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(communityDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(Community.class));
        Mockito.verify(postDao, Mockito.never()).findById(PostTestData.getPostId());
        Mockito.verify(communityDao, Mockito.never()).findById(CommunityTestData.getCommunityId());
        Mockito.reset(communityDao);
        Mockito.reset(postDao);
    }

}
