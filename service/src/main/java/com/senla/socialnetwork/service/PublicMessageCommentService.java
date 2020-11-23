package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PublicMessageCommentDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicMessageCommentService {
    List<PublicMessageCommentDto> getComments(int firstResult, int maxResults);

    PublicMessageCommentDto addComment(PublicMessageCommentDto publicMessageCommentDto);

    void updateComment(PublicMessageCommentDto publicMessageCommentDto);

    void deleteCommentByUser(HttpServletRequest request, Long commentId);

    void deleteComment(Long commentId);

}
