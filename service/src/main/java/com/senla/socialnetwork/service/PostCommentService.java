package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PostCommentDto;

import java.util.List;

public interface PostCommentService {
    List<PostCommentDto> getComments(int firstResult, int maxResults);

    void updateComment(PostCommentDto postCommentDto);

    void deleteCommentByUser(Long commentId);

    void deleteComment(Long commentId);

}
