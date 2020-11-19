package com.senla.socialnetwork.service.config;

import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.dto.CommunityDto;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static String getCommunityTittle() {
        return COMMUNITY_TITTLE;
    }

    public static Community getTestCommunity() {
        Community community = new Community();
        community.setId(COMMUNITY_ID);
        community.setAuthor(UserProfileTestData.getTestUserProfile());
        community.setTittle(COMMUNITY_TITTLE);
        community.setAuthor(UserProfileTestData.getTestUserProfile());
        return community;
    }

    public static CommunityDto getTestCommunityDto() {
        CommunityDto communityDto = new CommunityDto();
        communityDto.setId(COMMUNITY_ID);
        communityDto.setAuthor(UserProfileTestData.getTestUserProfileDto());
        communityDto.setTittle(COMMUNITY_TITTLE);
        communityDto.setAuthor(UserProfileTestData.getTestUserProfileDto());
        return communityDto;
    }

    public static List<Community> getTestCommunities() {
        Community communityOne = getTestCommunity();
        Community communityTwo = getTestCommunity();
        communityTwo.setId(COMMUNITY_OTHER_ID);
        return Arrays.asList(communityOne, communityTwo);
    }

    public static List<CommunityDto> getTestCommunitiesDto() {
        CommunityDto communityDtoOne = getTestCommunityDto();
        CommunityDto communityDtoTwo = getTestCommunityDto();
        communityDtoTwo.setId(COMMUNITY_OTHER_ID);
        List<CommunityDto> communitiesDto = new ArrayList<>();
        communitiesDto.add(communityDtoOne);
        communitiesDto.add(communityDtoTwo);
        return communitiesDto;
    }

}
