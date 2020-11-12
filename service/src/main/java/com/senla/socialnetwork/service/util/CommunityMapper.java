package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.*;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.dto.CommunityDto;

import java.util.List;
import java.util.stream.Collectors;

public class CommunityMapper {
    public static CommunityDto getCommunityDto(Community community) {
        CommunityDto communityDto = new CommunityDto();
        communityDto.setId(community.getId());
        communityDto.setCreationDate(community.getCreationDate());
        communityDto.setAuthor(UserProfileMapper.getUserProfileDto(community.getAuthor()));
        communityDto.setType(community.getType());
        communityDto.setTittle(community.getTittle());
        communityDto.setInformation(community.getInformation());
        communityDto.setDeleted(community.isDeleted());
        return communityDto;
    }

    public static List<CommunityDto> getCommunityDto(List<Community> communities) {
        return communities.stream()
                .map(CommunityMapper::getCommunityDto)
                .collect(Collectors.toList());
    }

    public static Community getCommunity(CommunityDto communityDto,
                                                 CommunityDao communityDao,
                                                 UserProfileDao userProfileDao,
                                                 LocationDao locationDao,
                                                 SchoolDao schoolDao,
                                                 UniversityDao universityDao) {
        Community community;
        if (communityDto.getId() == null) {
            community = new Community();
        } else {
            community = communityDao.findById(communityDto.getId());
        }
        community.setAuthor(UserProfileMapper.getUserProfile(
                communityDto.getAuthor(), userProfileDao, locationDao, schoolDao, universityDao));
        community.setCreationDate(communityDto.getCreationDate());
        community.setTittle(communityDto.getTittle());
        community.setInformation(communityDto.getInformation());
        community.setDeleted(communityDto.isDeleted());
        return community;
    }
}
