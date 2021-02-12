package com.senla.socialnetwork.dao.config;

import com.senla.socialnetwork.domain.Community;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CommunityTestData {
    private static final Long COMMUNITY_ID = 1L;
    private static final Long COMMUNITY_OTHER_ID = 2L;
    private static final Long RIGHT_NUMBER_COMMUNITIES = 2L;
    private static final String COMMUNITY_TITTLE = "Test";

    public static Long getCommunityId() {
        return COMMUNITY_ID;
    }

    public static Long getRightNumberCommunities() {
        return RIGHT_NUMBER_COMMUNITIES;
    }

    public static Community getTestCommunity() {
        Community community = new Community();
        community.setId(COMMUNITY_ID);
        community.setAuthor(UserProfileTestData.getTestUserProfile());
        community.setTitle(COMMUNITY_TITTLE);
        community.setCreationDate(new Date());
        community.setIsDeleted(false);
        return community;
    }

    public static List<Community> getTestCommunities() {
        Community communityOne = getTestCommunity();
        Community communityTwo = getTestCommunity();
        communityTwo.setId(COMMUNITY_OTHER_ID);
        return Arrays.asList(communityOne, communityTwo);
    }

}
