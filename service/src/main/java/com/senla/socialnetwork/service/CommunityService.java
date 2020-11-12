package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.CommunityDto;

import java.util.List;

public interface CommunityService {
    List<CommunityDto> getAllCommunities();

    List<CommunityDto> getCommunities(int firstResult, int maxResults);

    List<CommunityDto> getOwnCommunities(String email, int firstResult, int maxResults);

    List<CommunityDto> getSubscribedCommunities(String email, int firstResult, int maxResults);

    void subscribeToCommunity(String email, Long communityId);

    void unsubscribeFromCommunity(String email, Long communityId);

    CommunityDto addCommunity(CommunityDto communityDto);

    void updateCommunity(CommunityDto communityDto);

    void deleteCommunityByUser(String email, Long messageId);

}
