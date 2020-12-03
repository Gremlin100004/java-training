package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PostCommentDto;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostCommentService {
    List<PostCommentDto> getComments(int firstResult, int maxResults);

    void updateComment(HttpServletRequest request,
                       PostCommentDto postCommentDto,
                       SecretKey secretKey);

    void deleteCommentByUser(HttpServletRequest request, Long commentId, SecretKey secretKey);

    void deleteComment(Long commentId);

}
