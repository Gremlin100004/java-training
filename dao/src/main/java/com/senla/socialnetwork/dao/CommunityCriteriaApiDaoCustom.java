package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Community;

import java.util.List;

public interface CommunityCriteriaApiDaoCustom {

    List<Community> getCommunitiesSortiedByNumberOfSubscribers(final int firstResult, final int maxResults);

    List<Community> getSubscribedCommunitiesByEmail(final String email,  final int firstResult, final int maxResults);

}
