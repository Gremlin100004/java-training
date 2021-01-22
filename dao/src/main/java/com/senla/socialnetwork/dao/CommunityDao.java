package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.enumaration.CommunityType;

import java.util.List;

public interface CommunityDao extends GenericDao<Community, Long> {
    List<Community> getCommunities(int firstResult, int maxResults);

    List<Community> getCommunitiesByType(CommunityType communityType, int firstResult, int maxResults);

    List<Community> getCommunitiesSortiedByNumberOfSubscribers(int firstResult, int maxResults);

    List<Community> getCommunitiesByEmail(String email, int firstResult, int maxResults);

    List<Community> getSubscribedCommunitiesByEmail(String email, int firstResult, int maxResults);

    Community findByIdAndEmail(String email, Long communityId);

}
