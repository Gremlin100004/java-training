package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PublicMessageCommentDto;

import java.util.List;

public interface PublicMessageCommentService {
    List<PublicMessageCommentDto> getComments(int firstResult, int maxResults);

    PublicMessageCommentDto addComment(PublicMessageCommentDto publicMessageCommentDto);

    void updateComment(PublicMessageCommentDto publicMessageCommentDto);

    void deleteCommentByUser(String email, Long commentId);

    void deleteComment(Long commentId);

}
