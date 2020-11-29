package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PostCommentDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostCommentService {
    List<PostCommentDto> getComments(int firstResult, int maxResults);

    void updateComment(HttpServletRequest request, PostCommentDto postCommentDto);

    void deleteCommentByUser(HttpServletRequest request, Long commentId);

    void deleteComment(Long commentId);

}
