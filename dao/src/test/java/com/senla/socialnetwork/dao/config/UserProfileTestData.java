package com.senla.socialnetwork.dao.config;

import com.senla.socialnetwork.domain.UserProfile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserProfileTestData {
    private static final Long USER_PROFILE_ID = 1L;
    private static final Long FRIEND_ID = 3L;
    private static final Long USER_PROFILE_OTHER_ID = 2L;
    private static final Long FRIEND_OTHER_ID = 4L;
    private static final Date USER_PROFILE_CREATION_DATE = new Date();
    private static final String USER_PROFILE_NAME = "test";
    private static final String USER_PROFILE_EMAIL = "user1@test.com";
    private static final String USER_PROFILE_SURNAME = "test";

    public static Long getUserProfileId() {
        return USER_PROFILE_ID;
    }

    public static Long getUserProfileOtherId() {
        return USER_PROFILE_OTHER_ID;
    }

    public static Long getFriendId() {
        return FRIEND_ID;
    }

    public static Long getFriendOtherId() {
        return FRIEND_OTHER_ID;
    }

    public static String getUserProfileEmail() {
        return USER_PROFILE_EMAIL;
    }

    public static UserProfile getTestUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(USER_PROFILE_ID);
        userProfile.setRegistrationDate(USER_PROFILE_CREATION_DATE);
        userProfile.setSurname(USER_PROFILE_SURNAME);
        userProfile.setName(USER_PROFILE_NAME);
        return userProfile;
    }

    public static List<UserProfile> getTestUsersProfiles() {
        UserProfile userProfileOne = getTestUserProfile();
        UserProfile userProfileTwo = getTestUserProfile();
        userProfileTwo.setId(USER_PROFILE_OTHER_ID);
        List<UserProfile> userProfiles = new ArrayList<>();
        userProfiles.add(userProfileOne);
        userProfiles.add(userProfileTwo);
        return userProfiles;
    }

}
