package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.service.enumaration.UserProfileSortParameter;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.LocationMapper;
import com.senla.socialnetwork.service.util.SchoolMapper;
import com.senla.socialnetwork.service.util.UniversityMapper;
import com.senla.socialnetwork.service.util.UserProfileMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 0;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private SchoolDao schoolDao;
    @Autowired
    private UniversityDao universityDao;

    @Override
    @Transactional
    public UserProfileDto getUserProfileFiltered(String email) {
        log.debug("[getUserProfile]");
        log.trace("[email: {}]", email);
        return UserProfileMapper.getUserProfileDto(userProfileDao.findByEmail(email));
    }

    @Override
    @Transactional
    public List<UserProfileDto> getUserProfiles() {
        return UserProfileMapper.getUserProfileDto(userProfileDao.getAllRecords());
    }

    @Override
    @Transactional
    // ToDo maybe this method is wrong
    public UserProfileDto addUserProfile(UserProfileDto userProfileDto) {
        log.debug("[addUserProfile]");
        log.trace("[userProfileDto: {}]", userProfileDto);
        if (userProfileDto == null) {
            throw new BusinessException("Error, null user profile");
        }
        return UserProfileMapper.getUserProfileDto(userProfileDao.saveRecord(UserProfileMapper.getUserProfile(
            userProfileDto, userProfileDao, locationDao, schoolDao, universityDao)));
    }

    @Override
    @Transactional
    public void updateUserProfile(UserProfileDto userProfileDto) {
        log.debug("[updateUserProfile]");
        log.trace("[userProfileDto: {}]", userProfileDto);
        userProfileDao.updateRecord(
            UserProfileMapper.getUserProfile(userProfileDto, userProfileDao, locationDao, schoolDao, universityDao));
    }

    @Override
    @Transactional
    public List<UserProfileDto> getSortUserProfiles(UserProfileSortParameter sortParameter,
                                                    int firstResult,
                                                    int maxResults) {
        log.debug("[getUserProfiles]");
        log.trace("[sortParameter: {}]", sortParameter);
        List<UserProfile> userProfiles;
        if (sortParameter.equals(UserProfileSortParameter.BY_SURNAME)) {
            userProfiles = userProfileDao.getUserProfilesSortBySurname(firstResult, maxResults);
        } else if (sortParameter.equals(UserProfileSortParameter.BY_REGISTRATION_DATE)) {
            userProfiles = userProfileDao.getUserProfilesSortByRegistrationDate(firstResult, maxResults);
        } else {
            throw new BusinessException("Error, wrong sorting parameter");
        }
        return UserProfileMapper.getUserProfileDto(userProfiles);
    }

    @Override
    @Transactional
    public List<UserProfileDto> getUserProfileFiltered(LocationDto locationDto, int firstResult, int maxResults) {
        log.debug("[getUserProfileFiltered]");
        log.trace("[locationDto: {}]", locationDto);
        return UserProfileMapper.getUserProfileDto(userProfileDao.getUserProfilesFilteredByLocation(
                LocationMapper.getLocation(locationDto, locationDao), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<UserProfileDto> getUserProfileFiltered(SchoolDto schoolDto, int firstResult, int maxResults) {
        log.debug("[getUserProfileFiltered]");
        log.trace("[schoolDto: {}]", schoolDto);
        return UserProfileMapper.getUserProfileDto(
            userProfileDao.getUserProfilesFilteredBySchool(
                SchoolMapper.getSchool(schoolDto, schoolDao, locationDao), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<UserProfileDto> getUserProfileFiltered(UniversityDto universityDto, int firstResult, int maxResults) {
        log.debug("[getUserProfileFiltered]");
        log.trace("[schoolDto: {}]", universityDto);
        return UserProfileMapper.getUserProfileDto(userProfileDao.getUserProfilesFilteredByUniversity(
            UniversityMapper.getUniversity(universityDto, universityDao, locationDao), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<UserProfileDto> getUserProfilesFilteredByAge(
        Date startPeriodDate, Date endPeriodDate, int firstResult, int maxResults) {
        return UserProfileMapper.getUserProfileDto(
            userProfileDao.getUserProfilesFilteredByAge(startPeriodDate, endPeriodDate, firstResult, maxResults));
    }

    @Override
    @Transactional
    public UserProfileDto getFriendNearestDateOfBirth(String email) {
        UserProfile userProfile = userProfileDao.getNearestBirthdayByCurrentDate(email);
        if (userProfile == null) {
            userProfile = userProfileDao.getNearestBirthdayFromTheBeginningOfTheYear(email);
        }
        return userProfile == null ? null : UserProfileMapper.getUserProfileDto(userProfile);
    }

    @Override
    @Transactional
    public UserProfileDto getUserProfileFriend(String email, Long userProfileId) {
        log.debug("[getUserProfileMessages]");
        log.debug("[email: {}, userProfileId: {}]", email, userProfileId);
        return UserProfileMapper.getUserProfileDto(userProfileDao.getFriend(email, userProfileId));
    }

    @Override
    @Transactional
    public List<UserProfileDto> getUserProfileFriends(String email, int firstResult, int maxResults) {
        log.debug("[getUserProfileMessages]");
        log.debug("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        return UserProfileMapper.getUserProfileDto(userProfileDao.getFriends(email, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<UserProfileDto> getSortedFriendsOfUserProfile(String email,
                                                              UserProfileSortParameter sortParameter,
                                                              int firstResult,
                                                              int maxResults) {
        log.debug("[getSortedFriendsOfUserProfile]");
        log.debug("[email: {}, email: {}, firstResult: {}, maxResults: {}]",
            email, sortParameter, firstResult, maxResults);
        List<UserProfile> userProfiles;
        if (sortParameter.equals(UserProfileSortParameter.BY_BIRTHDAY)) {
            userProfiles = userProfileDao.getFriendsSortByAge(email, firstResult, maxResults);
        } else if (sortParameter.equals(UserProfileSortParameter.BY_NAME)) {
            userProfiles = userProfileDao.getFriendsSortByName(email, firstResult, maxResults);
        } else if (sortParameter.equals(UserProfileSortParameter.BY_NUMBER_OF_FRIENDS)) {
            userProfiles = userProfileDao.getFriendsSortByNumberOfFriends(email, firstResult, maxResults);
        } else {
            throw new BusinessException("Error, wrong sorting parameter");
        }
        return UserProfileMapper.getUserProfileDto(userProfiles);
    }

    @Override
    @Transactional
    public List<UserProfileDto> getUserProfileSignedFriends(String email, int firstResult, int maxResults) {
        log.debug("[getUserProfileMessages]");
        log.debug("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        return UserProfileMapper.getUserProfileDto(userProfileDao.getSignedFriends(email, firstResult, maxResults));
    }

    @Override
    @Transactional
    public void sendAFriendRequest(String email, Long userProfileId) {
        log.debug("[sendAFriendRequest]");
        log.debug("[email: {}, userProfileId: {}]", email, userProfileId);
        UserProfile ownProfile = userProfileDao.findByEmail(email);
        UserProfile userProfile = userProfileDao.getFutureFriend(email, userProfileId);
        if (userProfile == null) {
            throw new BusinessException("Error, this user is not suitable");
        }
        List<UserProfile> signedFriends = userProfileDao.getSignedFriends(email, FIRST_RESULT, MAX_RESULT);
        signedFriends.add(userProfile);
        ownProfile.setFriendshipRequests(signedFriends);
        userProfileDao.updateRecord(ownProfile);
    }

    @Override
    @Transactional
    public void confirmFriend(String email, Long userProfileId) {
        log.debug("[confirmFriend]");
        log.debug("[email: {}, userProfileId: {}]", email, userProfileId);
        UserProfile ownProfile = userProfileDao.findByEmail(email);
        UserProfile userProfile = userProfileDao.getSignedFriend(email, userProfileId);
        if (userProfile == null) {
            throw new BusinessException("Error, this user is not suitable");
        }
        List<UserProfile> signedFriends = userProfileDao.getSignedFriends(email, FIRST_RESULT, MAX_RESULT);
        signedFriends.remove(userProfile);
        ownProfile.setFriendshipRequests(signedFriends);
        List<UserProfile> friends = userProfileDao.getFriends(email, FIRST_RESULT, MAX_RESULT);
        friends.add(userProfile);
        ownProfile.setFriends(friends);
        userProfileDao.updateRecord(ownProfile);
    }

    @Override
    @Transactional
    public void removeUserFromFriends(String email, Long userProfileId) {
        log.debug("[removeUserFromFriends]");
        log.debug("[email: {}, userProfileId: {}]", email, userProfileId);
        UserProfile ownProfile = userProfileDao.findByEmail(email);
        UserProfile userProfile = userProfileDao.getFriend(email, userProfileId);
        if (userProfile == null) {
            throw new BusinessException("Error, this user is not suitable");
        }
        List<UserProfile> signedFriends = userProfileDao.getSignedFriends(email, FIRST_RESULT, MAX_RESULT);
        signedFriends.remove(userProfile);
        ownProfile.setFriendshipRequests(signedFriends);
        List<UserProfile> friends = userProfileDao.getFriends(email, FIRST_RESULT, MAX_RESULT);
        friends.add(userProfile);
        ownProfile.setFriends(friends);
        userProfileDao.updateRecord(ownProfile);
    }

}
