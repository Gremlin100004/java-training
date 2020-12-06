package com.senla.socialnetwork.service;

import com.senla.socialnetwork.domain.enumaration.CommunityType;
import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.dto.CommunityForCreateDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.dto.PostForCreationDto;

import java.util.List;

public interface CommunityService {
    List<CommunityDto> getAllCommunities(int firstResult, int maxResults);

    List<CommunityDto> getCommunities(int firstResult, int maxResults);

    List<CommunityDto> getCommunitiesSortiedByNumberOfSubscribers(int firstResult, int maxResults);

    List<CommunityDto> getCommunitiesFilteredByType(CommunityType communityType,
                                                    int firstResult,
                                                    int maxResults);

    List<CommunityDto> getOwnCommunities(int firstResult, int maxResults);

    List<CommunityDto> getSubscribedCommunities(int firstResult, int maxResults);

    void subscribeToCommunity(Long communityId);

    void unsubscribeFromCommunity(Long communityId);

    List<PostDto> getCommunityPosts(Long communityId, int firstResult, int maxResults);

    CommunityDto addCommunity(CommunityForCreateDto communityDto);

    void updateCommunity(CommunityDto communityDto);

    void deleteCommunityByUser(Long communityId);

    void deleteCommunity(Long communityId);

    PostDto addPostToCommunity(PostForCreationDto postDto, Long communityId);

}
