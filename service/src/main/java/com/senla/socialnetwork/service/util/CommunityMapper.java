package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.dto.CommunityForCreateDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CommunityMapper {
    public static CommunityDto getCommunityDto(final Community community) {
        CommunityDto communityDto = new CommunityDto();
        communityDto.setId(community.getId());
        communityDto.setCreationDate(community.getCreationDate());
        communityDto.setAuthor(UserProfileMapper.getUserProfileForIdentificationDto(community.getAuthor()));
        communityDto.setType(community.getType());
        communityDto.setTittle(community.getTittle());
        communityDto.setInformation(community.getInformation());
        communityDto.setDeleted(community.isDeleted());
        return communityDto;
    }

    public static List<CommunityDto> getCommunityDto(final List<Community> communities) {
        return communities.stream()
                .map(CommunityMapper::getCommunityDto)
                .collect(Collectors.toList());
    }

    public static Community getCommunity(final CommunityDto communityDto,
                                         final CommunityDao communityDao,
                                         final UserProfileDao userProfileDao) {
        Community community = communityDao.findById(communityDto.getId());
        community.setAuthor(UserProfileMapper.getUserProfileFromUserProfileForIdentificationDto(
                communityDto.getAuthor(), userProfileDao));
        community.setCreationDate(communityDto.getCreationDate());
        community.setTittle(communityDto.getTittle());
        community.setInformation(communityDto.getInformation());
        community.setDeleted(communityDto.isDeleted());
        return community;
    }

    public static Community getNewCommunity(final CommunityForCreateDto communityDto,
                                            final UserProfile userProfile) {
        Community community = new Community();
        if (communityDto.getTittle() != null) {
            community.setTittle(communityDto.getTittle());
        }
        if (communityDto.getInformation() != null) {
            community.setInformation(communityDto.getInformation());
        }
        community.setCreationDate(new Date());
        community.setAuthor(userProfile);
        return community;
    }
    
}
