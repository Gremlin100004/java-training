package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PostCommentDto;

import java.util.List;

public interface PostCommentService {
    List<PostCommentDto> getComments(int firstResult, int maxResults);

    List<PostCommentDto> getPostComments(Long postId, int firstResult, int maxResults);

    PostCommentDto addComment(PostCommentDto postCommentDto);

    void updateComment(PostCommentDto postCommentDto);

    void deleteCommentByUser(String email, Long commentId);

    void deleteComment(Long commentId);

}
