package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.domain.UserProfile;

import java.util.List;

public interface PublicMessageDao extends GenericDao<PublicMessage, Long> {
    List<PublicMessage> getFriendsMessages(UserProfile ownProfile, int firstResult, int maxResults);
}
