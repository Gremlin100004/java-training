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
import com.senla.socialnetwork.service.util.JwtUtil;
import com.senla.socialnetwork.service.mapper.PrivateMessageMapper;
import com.senla.socialnetwork.service.mapper.UserProfileMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
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
    public UserProfileDto getUserProfile(final HttpServletRequest request, final SecretKey secretKey) {
        log.debug("[getUserProfile]");
        log.trace("[request: {}]", request);
        return UserProfileMapper.getUserProfileDto(userProfileDao.findByEmail(JwtUtil.extractUsername(JwtUtil.getToken(
            request), secretKey)));
    }

    @Override
    @Transactional
    public void updateUserProfile(final UserProfileDto userProfileDto,
                                  final HttpServletRequest request,
                                  final SecretKey secretKey) {
        log.debug("[updateUserProfile]");
        log.trace("[userProfileDto: {}, request: {}]", userProfileDto, request);
        userProfileDao.updateRecord(UserProfileMapper.getUserProfile(
            userProfileDto, userProfileDao, JwtUtil.extractUsername(JwtUtil.getToken(
                request), secretKey),  locationDao, schoolDao, universityDao));
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
    public UserProfileForIdentificationDto getFriendNearestDateOfBirth(final HttpServletRequest request,
                                                                       final SecretKey secretKey) {
        log.debug("[getFriendNearestDateOfBirth]");
        log.trace("[request: {}]", request);
        String email = JwtUtil.extractUsername(JwtUtil.getToken(request), secretKey);
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
    public List<UserProfileForIdentificationDto> getUserProfileFriends(final HttpServletRequest request,
                                                                       final int firstResult,
                                                                       final int maxResults,
                                                                       final SecretKey secretKey) {
        log.debug("[getUserProfileFriends]");
        log.debug("[request: {}, firstResult: {}, maxResults: {}]", request, firstResult, maxResults);
        return UserProfileMapper.getUserProfileForIdentificationDto(userProfileDao.getFriends(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<UserProfileForIdentificationDto> getSortedFriendsOfUserProfile(final HttpServletRequest request,
                                                                               final UserProfileFriendSortParameter sortParameter,
                                                                               final int firstResult,
                                                                               final int maxResults,
                                                                               final SecretKey secretKey) {
        log.debug("[getSortedFriendsOfUserProfile]");
        log.debug("[request: {}, email: {}, firstResult: {}, maxResults: {}]",
            request, sortParameter, firstResult, maxResults);
        String email = JwtUtil.extractUsername(JwtUtil.getToken(request), secretKey);
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
    public List<UserProfileForIdentificationDto> getUserProfileSignedFriends(final HttpServletRequest request,
                                                                             final int firstResult,
                                                                             final int maxResults,
                                                                             final SecretKey secretKey) {
        log.debug("[getUserProfileSignedFriends]");
        log.debug("[request: {}, firstResult: {}, maxResults: {}]", request, firstResult, maxResults);
        return UserProfileMapper.getUserProfileForIdentificationDto(userProfileDao.getSignedFriends(
            JwtUtil.extractUsername(JwtUtil.getToken(request), secretKey), firstResult, maxResults));
    }

    @Override
    @Transactional
    public void sendAFriendRequest(final HttpServletRequest request,
                                   final  Long userProfileId,
                                   final SecretKey secretKey) {
        log.debug("[sendAFriendRequest]");
        log.debug("[request: {}, userProfileId: {}]", request, userProfileId);
        String email = JwtUtil.extractUsername(JwtUtil.getToken(request), secretKey);
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
    public void confirmFriend(final HttpServletRequest request, final Long userProfileId, final SecretKey secretKey) {
        log.debug("[confirmFriend]");
        log.debug("[request: {}, userProfileId: {}]", request, userProfileId);
        String email = JwtUtil.extractUsername(JwtUtil.getToken(request), secretKey);
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
    public void removeUserFromFriends(final HttpServletRequest request,
                                      final Long userProfileId,
                                      final SecretKey secretKey) {
        log.debug("[removeUserFromFriends]");
        log.debug("[request: {}, userProfileId: {}]", request, userProfileId);
        String email = JwtUtil.extractUsername(JwtUtil.getToken(request), secretKey);
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
    public List<PrivateMessageDto> getDialogue(final HttpServletRequest request,
                                               final Long userProfileId,
                                               final int firstResult,
                                               final int maxResults,
                                               final SecretKey secretKey) {
        log.debug("[getDialogue]");
        log.debug("[request: {}, userProfileId: {}, firstResult: {}, maxResults: {}]",
            request, userProfileId, firstResult, maxResults);
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getDialogue(JwtUtil.extractUsername(JwtUtil.getToken(
                request), secretKey), userProfileId, firstResult, maxResults));
    }

}
