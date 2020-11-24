package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PostCommentDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostCommentService {
    List<PostCommentDto> getComments(int firstResult, int maxResults);

    PostCommentDto addComment(HttpServletRequest request, PostCommentDto postCommentDto);

    void updateComment(HttpServletRequest request, PostCommentDto postCommentDto);

    void deleteCommentByUser(String email, Long commentId);

    void deleteComment(Long commentId);

}
