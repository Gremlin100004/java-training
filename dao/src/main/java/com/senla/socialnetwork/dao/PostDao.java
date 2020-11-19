package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Post;

import java.util.List;

public interface PostDao extends GenericDao<Post, Long> {
    List<Post> getByCommunityId(Long communityId, int firstResult, int maxResults);

    List<Post> getByEmail(String email, int firstResult, int maxResults);

    Post findByIdAndEmail(String email, Long postId);
}
