package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.dto.PostCommentForCreateDto;
import com.senla.socialnetwork.dto.PostDto;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostService {
    List<PostDto> getPosts(int firstResult, int maxResults);

    List<PostDto> getPostsFromSubscribedCommunities(HttpServletRequest request,
                                                    int firstResult,
                                                    int maxResults,
                                                    SecretKey secretKey);

    void updatePost(PostDto postDto);

    void deletePost(Long postId);

    void deletePostByUser(HttpServletRequest request, Long postId, SecretKey secretKey);

    List<PostCommentDto> getPostComments(Long postId, int firstResult, int maxResults);

    PostCommentDto addComment(HttpServletRequest request,
                              Long postId,
                              PostCommentForCreateDto postCommentDto,
                              SecretKey secretKey);

}
