package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> getPosts(int firstResult, int maxResults);

    List<PostDto> getPostsFromSubscribedCommunities(String email, int firstResult, int maxResults);

    void updatePost(PostDto postDto);

    void deletePost(Long postId);

    void deletePostByUser(String email, Long postId);

    List<PostCommentDto> getPostComments(Long postId, int firstResult, int maxResults);

}
