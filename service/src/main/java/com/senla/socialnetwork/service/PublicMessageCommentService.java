package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageCommentForCreateDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicMessageCommentService {
    List<PublicMessageCommentDto> getComments(int firstResult, int maxResults);

    PublicMessageCommentDto addComment(HttpServletRequest request,
                                       PublicMessageCommentForCreateDto publicMessageCommentDto);

    void updateComment(HttpServletRequest request, PublicMessageCommentDto publicMessageCommentDto);

    void deleteCommentByUser(HttpServletRequest request, Long commentId);

    void deleteComment(Long commentId);

}
