package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PostCommentDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostCommentService {
    @Transactional
    List<PostCommentDto> getComments();

    @Transactional
    List<PostCommentDto> getPostComments(Long postId, int firstResult, int maxResults);

    @Transactional
    PostCommentDto addComment(PostCommentDto postCommentDto);

    @Transactional
    void updateComment(PostCommentDto postCommentDto);

    @Transactional
    void deleteCommentByUser(String email, Long commentId);

    @Transactional
    void deleteComment(Long commentId);
}
