package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.PublicMessageComment;

import java.util.List;

public interface PublicMessageCommentDao extends GenericDao<PublicMessageComment, Long> {
    PublicMessageComment findByIdAndEmail(String email, Long commentId);

    List<PublicMessageComment> getPublicMessageComments(Long publicMessageId, int firstResult, int maxResults);
}
