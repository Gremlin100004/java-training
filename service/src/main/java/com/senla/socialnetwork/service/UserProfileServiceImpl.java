package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PrivateMessageDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.service.enumaration.UserProfileSortParameter;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.JwtUtil;
import com.senla.socialnetwork.service.util.LocationMapper;
import com.senla.socialnetwork.service.util.PrivateMessageMapper;
import com.senla.socialnetwork.service.util.PublicMessageMapper;
import com.senla.socialnetwork.service.util.SchoolMapper;
import com.senla.socialnetwork.service.util.UniversityMapper;
import com.senla.socialnetwork.service.util.UserProfileMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private PublicMessageDao publicMessageDao;
    @Value("${com.senla.socialnetwork.JwtUtil.secret-key:qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq}")
    private String secretKey;

    @Override
    @Transactional
    public List<UserProfileDto> getUserProfiles(final int firstResult, final int maxResults) {
        log.debug("[getUserProfiles]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return UserProfileMapper.getUserProfileDto(userProfileDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public UserProfileDto getUserProfile(final HttpServletRequest request) {
        log.debug("[getUserProfile]");
        log.trace("[request: {}]", request);
        return UserProfileMapper.getUserProfileDto(userProfileDao.findByEmail(JwtUtil.extractUsername(JwtUtil.getToken(
            request), secretKey)));
    }

    @Override
    @Transactional
    public void updateUserProfile(final UserProfileDto userProfileDto) {
        log.debug("[updateUserProfile]");
        log.trace("[userProfileDto: {}]", userProfileDto);
        userProfileDao.updateRecord(
            UserProfileMapper.getUserProfile(userProfileDto, userProfileDao, locationDao, schoolDao, universityDao));
    }

    @Override
    @Transactional
    public List<UserProfileDto> getSortUserProfiles(final UserProfileSortParameter sortParameter,
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
        return UserProfileMapper.getUserProfileDto(userProfiles);
    }

    @Override
    @Transactional
    public List<UserProfileDto> getUserProfiles(final LocationDto locationDto,
                                                final int firstResult,
                                                final int maxResults) {
        log.debug("[getUserProfiles]");
        log.trace("[locationDto: {}, firstResult: {}, maxResults: {}]", locationDto, firstResult, maxResults);
        return UserProfileMapper.getUserProfileDto(userProfileDao.getUserProfilesFilteredByLocation(
                LocationMapper.getLocation(locationDto, locationDao), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<UserProfileDto> getUserProfiles(final SchoolDto schoolDto,
                                                final int firstResult,
                                                final int maxResults) {
        log.debug("[getUserProfiles]");
        log.trace("[schoolDto: {}, firstResult: {}, maxResults: {}]", schoolDto, firstResult, maxResults);
        return UserProfileMapper.getUserProfileDto(
            userProfileDao.getUserProfilesFilteredBySchool(
                SchoolMapper.getSchool(schoolDto, schoolDao, locationDao), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<UserProfileDto> getUserProfiles(final UniversityDto universityDto,
                                                final int firstResult,
                                                final int maxResults) {
        log.debug("[getUserProfiles]");
        log.trace("[universityDto: {}, firstResult: {}, maxResults: {}]", universityDto, firstResult, maxResults);
        return UserProfileMapper.getUserProfileDto(userProfileDao.getUserProfilesFilteredByUniversity(
            UniversityMapper.getUniversity(universityDto, universityDao, locationDao), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<UserProfileDto> getUserProfilesFilteredByAge(final Date startPeriodDate,
                                                             final Date endPeriodDate,
                                                             final int firstResult,
                                                             final int maxResults) {
        log.debug("[getUserProfilesFilteredByAge]");
        log.trace("[startPeriodDate: {}, endPeriodDate: {}, firstResult: {}, maxResults: {}]",
            startPeriodDate, endPeriodDate, firstResult, maxResults);
        return UserProfileMapper.getUserProfileDto(
            userProfileDao.getUserProfilesFilteredByAge(startPeriodDate, endPeriodDate, firstResult, maxResults));
    }

    @Override
    @Transactional
    public UserProfileDto getFriendNearestDateOfBirth(final HttpServletRequest request) {
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
        return UserProfileMapper.getUserProfileDto(userProfile);
    }

    @Override
    @Transactional
    public UserProfileDto getUserProfileFriend(final HttpServletRequest request, final Long userProfileId) {
        log.debug("[getUserProfileFriend]");
        log.debug("[request: {}, userProfileId: {}]", request, userProfileId);
        return UserProfileMapper.getUserProfileDto(userProfileDao.getFriend(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey), userProfileId));
    }

    @Override
    @Transactional
    public List<UserProfileDto> getUserProfileFriends(final HttpServletRequest request,
                                                      final int firstResult,
                                                      final int maxResults) {
        log.debug("[getUserProfileFriends]");
        log.debug("[request: {}, firstResult: {}, maxResults: {}]", request, firstResult, maxResults);
        return UserProfileMapper.getUserProfileDto(userProfileDao.getFriends(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<UserProfileDto> getSortedFriendsOfUserProfile(final HttpServletRequest request,
                                                              final UserProfileSortParameter sortParameter,
                                                              final int firstResult,
                                                              final int maxResults) {
        log.debug("[getSortedFriendsOfUserProfile]");
        log.debug("[request: {}, email: {}, firstResult: {}, maxResults: {}]",
            request, sortParameter, firstResult, maxResults);
        String email = JwtUtil.extractUsername(JwtUtil.getToken(request), secretKey);
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
    public List<UserProfileDto> getUserProfileSignedFriends(final HttpServletRequest request,
                                                            final int firstResult,
                                                            final int maxResults) {
        log.debug("[getUserProfileSignedFriends]");
        log.debug("[request: {}, firstResult: {}, maxResults: {}]", request, firstResult, maxResults);
        return UserProfileMapper.getUserProfileDto(userProfileDao.getSignedFriends(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey), firstResult, maxResults));
    }

    @Override
    @Transactional
    public void sendAFriendRequest(final HttpServletRequest request, final  Long userProfileId) {
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
        List<UserProfile> signedFriends = userProfileDao.getSignedFriends(email, FIRST_RESULT, MAX_RESULT);
        signedFriends.add(userProfile);
        ownProfile.setFriendshipRequests(signedFriends);
        userProfileDao.updateRecord(ownProfile);
    }

    @Override
    @Transactional
    public void confirmFriend(final HttpServletRequest request, final Long userProfileId) {
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
    public void removeUserFromFriends(final HttpServletRequest request, final Long userProfileId) {
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
    public void deleteUserProfile(final Long userProfileId) {
        log.debug("[deleteUserProfile]");
        log.debug("[userProfileId: {}]", userProfileId);
        if (userProfileDao.findById(userProfileId) == null) {
            throw new BusinessException("Error, there is no such profile");
        }
        userProfileDao.deleteRecord(userProfileId);
    }

    @Override
    @Transactional
    public List<PrivateMessageDto> getPrivateMessages(final HttpServletRequest request,
                                                      final int firstResult,
                                                      final int maxResults) {
        log.debug("[getPrivateMessages]");
        log.debug("[request: {}, firstResult: {}, maxResults: {}]", request, firstResult, maxResults);
        String email = JwtUtil.extractUsername(JwtUtil.getToken(request), secretKey);
        UserProfile ownProfile = userProfileDao.findByEmail(email);
        if (ownProfile == null) {
            throw new BusinessException("Error, user with this email does not exist");
        }
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getByEmail(email, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PrivateMessageDto> getDialogue(final HttpServletRequest request,
                                               final Long userProfileId,
                                               final int firstResult,
                                               final int maxResults) {
        log.debug("[getDialogue]");
        log.debug("[request: {}, userProfileId: {}, firstResult: {}, maxResults: {}]",
            request, userProfileId, firstResult, maxResults);
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getDialogue(JwtUtil.extractUsername(JwtUtil.getToken(
                request), secretKey), userProfileId, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PrivateMessageDto> getUnreadMessages(final HttpServletRequest request,
                                                     final int firstResult,
                                                     final int maxResults) {
        log.debug("[getUnreadMessages]");
        log.debug("[request: {}, firstResult: {}, maxResults: {}]", request, firstResult, maxResults);
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getUnreadMessages(JwtUtil.extractUsername(JwtUtil.getToken(
                request), secretKey), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PublicMessageDto> getFriendsPublicMessages(final HttpServletRequest request,
                                                           final int firstResult,
                                                           final int maxResults) {
        log.debug("[getFriendsPublicMessages]");
        log.debug("[request: {}, firstResult: {}, maxResults: {}]", request, firstResult, maxResults);
        return PublicMessageMapper.getPublicMessageDto(
            publicMessageDao.getFriendsMessages(JwtUtil.extractUsername(JwtUtil.getToken(
                request), secretKey), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PublicMessageDto> getPublicMessages(final HttpServletRequest request, final int firstResult, final int maxResults) {
        log.debug("[getPublicMessages]");
        log.debug("[request: {}, firstResult: {}, maxResults: {}]", request, firstResult, maxResults);
        return PublicMessageMapper.getPublicMessageDto(publicMessageDao.getByEmail(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey), firstResult, maxResults));
    }

}
