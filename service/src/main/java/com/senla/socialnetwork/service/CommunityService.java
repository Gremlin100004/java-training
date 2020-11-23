package com.senla.socialnetwork.service;

import com.senla.socialnetwork.domain.enumaration.CommunityType;
import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.dto.PostDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommunityService {
    List<CommunityDto> getAllCommunities(int firstResult, int maxResults);

    List<CommunityDto> getCommunities(int firstResult, int maxResults);

    List<CommunityDto> getCommunitiesSortiedByNumberOfSubscribers(int firstResult, int maxResults);

    List<CommunityDto> getCommunitiesFilteredByType(CommunityType communityType, int firstResult, int maxResults);

    List<CommunityDto> getOwnCommunities(HttpServletRequest request, int firstResult, int maxResults);

    List<CommunityDto> getSubscribedCommunities(HttpServletRequest request, int firstResult, int maxResults);

    void subscribeToCommunity(HttpServletRequest request, Long communityId);

    void unsubscribeFromCommunity(HttpServletRequest request, Long communityId);

    List<PostDto> getCommunityPosts(Long communityId, int firstResult, int maxResults);

    CommunityDto addCommunity(CommunityDto communityDto);

    void updateCommunity(CommunityDto communityDto);

    void deleteCommunityByUser(HttpServletRequest request, Long communityId);

    void deleteCommunity(Long communityId);

    void addPostToCommunity(HttpServletRequest request, PostDto postDto, Long communityId);

}
