package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PrivateMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.dto.UserProfileForIdentificationDto;
import com.senla.socialnetwork.service.enumaration.UserProfileFriendSortParameter;
import com.senla.socialnetwork.service.enumaration.UserProfileSortParameter;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.PrivateMessageMapper;
import com.senla.socialnetwork.service.mapper.UserProfileMapper;
import com.senla.socialnetwork.service.security.UserPrincipal;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Autowired
    private PrivateMessageDao privateMessageDao;

    @Override
    @Transactional
    public List<UserProfileForIdentificationDto> getUserProfiles(final int firstResult, final int maxResults) {
        log.debug("[getUserProfiles]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return UserProfileMapper.getUserProfileForIdentificationDto(userProfileDao.getAllRecords(
            firstResult, maxResults));
    }

    @Override
    @Transactional
    public UserProfileDto getUserProfile() {
        return UserProfileMapper.getUserProfileDto(userProfileDao.findByEmail(getUserName()));
    }

    @Override
    @Transactional
    public void updateUserProfile(final UserProfileDto userProfileDto) {
        log.debug("[updateUserProfile]");
        log.trace("[userProfileDto: {}]", userProfileDto);
        userProfileDao.updateRecord(UserProfileMapper.getUserProfile(
            userProfileDto, userProfileDao, getUserName(),  locationDao, schoolDao, universityDao));
    }

    @Override
    @Transactional
    public List<UserProfileForIdentificationDto> getSortUserProfiles(final UserProfileSortParameter sortParameter,
                                                                     final int firstResult,
                                                                     final int maxResults) {
        log.debug("[getSortUserProfiles]");
        log.trace("[sortParameter: {}, firstResult: {}, maxResults: {}]", sortParameter, firstResult, maxResults);
        List<UserProfile> userProfiles;
        if (sortParameter.equals(UserProfileSortParameter.BY_SURNAME)) {
            userProfiles = userProfileDao.getUserProfilesSortBySurname(firstResult, maxResults);
        } else if (sortParameter.equals(UserProfileSortParameter.BY_REGISTRATION_DATE)) {
            userProfiles = userProfileDao.getUserProfilesSortByRegistrationDate(firstResult, maxResults);
        } else {
            throw new BusinessException("Error, wrong sorting parameter");
        }
        return UserProfileMapper.getUserProfileForIdentificationDto(userProfiles);
    }

    @Override
    @Transactional
    public List<UserProfileForIdentificationDto> getUserProfilesByLocationId(final Long locationId,
                                                                             final int firstResult,
                                                                             final int maxResults) {
        log.debug("[getUserProfiles]");
        log.trace("[locationDto: {}, firstResult: {}, maxResults: {}]", locationId, firstResult, maxResults);
        return UserProfileMapper.getUserProfileForIdentificationDto(
            userProfileDao.getUserProfilesFilteredByLocation(locationId, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<UserProfileForIdentificationDto> getUserProfilesBySchoolId(final Long schoolId,
                                                                           final int firstResult,
                                                                           final int maxResults) {
        log.debug("[getUserProfiles]");
        log.trace("[schoolDto: {}, firstResult: {}, maxResults: {}]", schoolId, firstResult, maxResults);
        return UserProfileMapper.getUserProfileForIdentificationDto(
            userProfileDao.getUserProfilesFilteredBySchool(schoolId, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<UserProfileForIdentificationDto> getUserProfilesByUniversityId(final Long universityId,
                                                                               final int firstResult,
                                                                               final int maxResults) {
        log.debug("[getUserProfiles]");
        log.trace("[universityDto: {}, firstResult: {}, maxResults: {}]", universityId, firstResult, maxResults);
        return UserProfileMapper.getUserProfileForIdentificationDto(
            userProfileDao.getUserProfilesFilteredByUniversity(universityId, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<UserProfileForIdentificationDto> getUserProfilesFilteredByAge(final Date startPeriodDate,
                                                                              final Date endPeriodDate,
                                                                              final int firstResult,
                                                                              final int maxResults) {
        log.debug("[getUserProfilesFilteredByAge]");
        log.trace("[startPeriodDate: {}, endPeriodDate: {}, firstResult: {}, maxResults: {}]",
                  startPeriodDate, endPeriodDate, firstResult, maxResults);
        return UserProfileMapper.getUserProfileForIdentificationDto(
            userProfileDao.getUserProfilesFilteredByAge(startPeriodDate, endPeriodDate, firstResult, maxResults));
    }

    @Override
    @Transactional
    public UserProfileForIdentificationDto getFriendNearestDateOfBirth() {
        String email = getUserName();
        UserProfile userProfile = userProfileDao.getNearestBirthdayByCurrentDate(email);
        if (userProfile == null) {
            userProfile = userProfileDao.getNearestBirthdayFromTheBeginningOfTheYear(email);
        }
        if (userProfile == null) {
            return null;
        }
        return UserProfileMapper.getUserProfileForIdentificationDto(userProfile);
    }

    @Override
    @Transactional
    public UserProfileDto getUserProfileDetails(final Long userProfileId) {
        log.debug("[getUserProfileFriend]");
        log.debug("[userProfileId: {}]", userProfileId);
        return UserProfileMapper.getUserProfileDto(userProfileDao.findById(userProfileId));
    }

    @Override
    @Transactional
    public List<UserProfileForIdentificationDto> getUserProfileFriends(final int firstResult, final int maxResults) {
        log.debug("[getUserProfileFriends]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return UserProfileMapper.getUserProfileForIdentificationDto(userProfileDao.getFriends(
            getUserName(), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<UserProfileForIdentificationDto> getSortedFriendsOfUserProfile(final UserProfileFriendSortParameter sortParameter,
                                                                               final int firstResult,
                                                                               final int maxResults) {
        log.debug("[getSortedFriendsOfUserProfile]");
        log.debug("[sortParameter: {}, firstResult: {}, maxResults: {}]",
                  sortParameter, firstResult, maxResults);
        String email = getUserName();
        List<UserProfile> userProfiles;
        if (sortParameter.equals(UserProfileFriendSortParameter.BY_BIRTHDAY)) {
            userProfiles = userProfileDao.getFriendsSortByAge(email, firstResult, maxResults);
        } else if (sortParameter.equals(UserProfileFriendSortParameter.BY_NAME)) {
            userProfiles = userProfileDao.getFriendsSortByName(email, firstResult, maxResults);
        } else if (sortParameter.equals(UserProfileFriendSortParameter.BY_NUMBER_OF_FRIENDS)) {
            userProfiles = userProfileDao.getFriendsSortByNumberOfFriends(email, firstResult, maxResults);
        } else {
            throw new BusinessException("Error, wrong sorting parameter");
        }
        return UserProfileMapper.getUserProfileForIdentificationDto(userProfiles);
    }

    @Override
    @Transactional
    public List<UserProfileForIdentificationDto> getUserProfileSignedFriends(final int firstResult,
                                                                             final int maxResults) {
        log.debug("[getUserProfileSignedFriends]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return UserProfileMapper.getUserProfileForIdentificationDto(userProfileDao.getSignedFriends(
            getUserName(), firstResult, maxResults));
    }

    @Override
    @Transactional
    public void sendAFriendRequest(final  Long userProfileId) {
        log.debug("[sendAFriendRequest]");
        log.debug("[userProfileId: {}]", userProfileId);
        String email = getUserName();
        UserProfile ownProfile = userProfileDao.findByEmail(email);
        if (ownProfile == null) {
            throw new BusinessException("Error, this user is not exist");
        }
        UserProfile userProfile = userProfileDao.getFutureFriend(email, userProfileId);
        if (userProfile == null) {
            throw new BusinessException("Error, this user is not suitable");
        }
        userProfile.getFriendshipRequests().add(ownProfile);
        userProfileDao.updateRecord(userProfile);
    }

    @Override
    @Transactional
    public void confirmFriend(final Long userProfileId) {
        log.debug("[confirmFriend]");
        log.debug("[userProfileId: {}]", userProfileId);
        String email = getUserName();
        UserProfile ownProfile = userProfileDao.findByEmail(email);
        if (ownProfile == null) {
            throw new BusinessException("Error, this user is not exist");
        }
        UserProfile userProfile = userProfileDao.getSignedFriend(email, userProfileId);
        if (userProfile == null) {
            throw new BusinessException("Error, this user is not suitable");
        }
        ownProfile.getFriendshipRequests().remove(userProfile);
        ownProfile.getFriends().add(userProfile);
        userProfileDao.updateRecord(ownProfile);
    }

    @Override
    @Transactional
    public void removeUserFromFriends(final Long userProfileId) {
        log.debug("[removeUserFromFriends]");
        log.debug("[userProfileId: {}]", userProfileId);
        String email = getUserName();
        UserProfile ownProfile = userProfileDao.findByEmail(email);
        if (ownProfile == null) {
            throw new BusinessException("Error, this user is not exist");
        }
        UserProfile userProfile = userProfileDao.getFriend(email, userProfileId);
        if (userProfile == null) {
            throw new BusinessException("Error, this user is not suitable");
        }
        List<UserProfile> friends = userProfileDao.getFriends(email, FIRST_RESULT, MAX_RESULT);
        friends.remove(userProfile);
        ownProfile.setFriends(friends);
        userProfileDao.updateRecord(ownProfile);
    }

    @Override
    @Transactional
    public void deleteUserProfile(final Long userProfileId) {
        log.debug("[deleteUserProfile]");
        log.debug("[userProfileId: {}]", userProfileId);
        UserProfile userProfile = userProfileDao.findById(userProfileId);
        if (userProfile == null) {
            throw new BusinessException("Error, there is no such profile");
        }
        userProfileDao.deleteRecord(userProfile);
    }

    @Override
    @Transactional
    public List<PrivateMessageDto> getDialogue(final Long userProfileId, final int firstResult, final int maxResults) {
        log.debug("[getDialogue]");
        log.debug("[userProfileId: {}, firstResult: {}, maxResults: {}]",
                  userProfileId, firstResult, maxResults);
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getDialogue(getUserName(), userProfileId, firstResult, maxResults));
    }

    private String getUserName() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        return userPrincipal.getUsername();
    }

}
