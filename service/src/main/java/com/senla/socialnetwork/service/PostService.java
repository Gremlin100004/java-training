package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PostDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostService {
    List<PostDto> getCommunityPosts(Long communityId, int firstResult, int maxResults);

    List<PostDto> getPostsFromSubscribedCommunities(String email, int firstResult, int maxResults);

    void addPostToCommunity(String email, PostDto postDto, Long communityId);

    void updatePost(PostDto postDto);

    void deletePost(Long postId);

}
