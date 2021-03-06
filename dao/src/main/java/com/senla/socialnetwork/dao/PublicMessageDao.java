package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.PublicMessage;

import java.util.List;

public interface PublicMessageDao extends GenericDao<PublicMessage, Long> {
    List<PublicMessage> getFriendsMessages(String email, int firstResult, int maxResults);

    List<PublicMessage> findByEmail(String email, int firstResult, int maxResults);

    PublicMessage findByIdAndEmail(String email, Long messageId);

}
