package com.senla.socialnetwork.controller.config;

import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.dto.CommunityForCreateDto;
import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.enumaration.CommunityType;

import java.util.ArrayList;
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
        community.setAuthor(UserProfileTestData.getTestUserProfile());
        community.setIsDeleted(false);
        return community;
    }

    public static CommunityDto getCommunityDto() {
        CommunityDto communityDto = new CommunityDto();
        communityDto.setId(COMMUNITY_ID);
        communityDto.setCreationDate(new Date());
        communityDto.setAuthor(UserProfileTestData.getTestUserProfileForIdentificationDto());
        communityDto.setTitle(COMMUNITY_TITTLE);
        communityDto.setType(CommunityType.BUSINESS);
        communityDto.setAuthor(UserProfileTestData.getTestUserProfileForIdentificationDto());
        return communityDto;
    }

    public static CommunityForCreateDto getCommunityForCreationDto() {
        CommunityForCreateDto communityDto = new CommunityForCreateDto();
        communityDto.setType(CommunityType.BUSINESS);
        communityDto.setTitle(COMMUNITY_TITTLE);
        return communityDto;
    }

    public static List<Community> getTestCommunities() {
        Community communityOne = getTestCommunity();
        Community communityTwo = getTestCommunity();
        communityTwo.setId(COMMUNITY_OTHER_ID);
        return new ArrayList<Community>() {
            {
                add(communityOne);
                add(communityTwo);
            }
        };
    }

    public static List<CommunityDto> getCommunitiesDto() {
        CommunityDto communityDtoOne = getCommunityDto();
        CommunityDto communityDtoTwo = getCommunityDto();
        communityDtoTwo.setId(COMMUNITY_OTHER_ID);
        List<CommunityDto> communitiesDto = new ArrayList<>();
        communitiesDto.add(communityDtoOne);
        communitiesDto.add(communityDtoTwo);
        return communitiesDto;
    }

}
