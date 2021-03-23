package com.senla.socialnetwork.dao.springdata;

import com.senla.socialnetwork.model.Community;

import java.util.List;

public interface CommunityCriteriaApiDaoCustom {

    List<Community> getCommunitiesSortiedByNumberOfSubscribers(int firstResult, int maxResults);

    List<Community> getSubscribedCommunitiesByEmail(String email, int firstResult, int maxResults);

}
