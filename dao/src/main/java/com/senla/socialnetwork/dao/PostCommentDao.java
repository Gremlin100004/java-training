package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.PostComment;

import java.util.List;

public interface PostCommentDao extends GenericDao<PostComment, Long> {
    PostComment findByIdAndEmail(String email, Long commentId);

    List<PostComment> getPostComments(Long postId, int firstResult, int maxResults);
}
