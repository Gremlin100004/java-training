package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PublicMessageCommentDto;

import java.util.List;

public interface PublicMessageCommentService {
    List<PublicMessageCommentDto> getPublicMessageComments(Long publicMessageId);

    PublicMessageCommentDto addComment(PublicMessageCommentDto publicMessageCommentDto);

    void updateComment(PublicMessageCommentDto publicMessageCommentDto);

    void deleteCommentByUser(String email, Long messageId);

    void deleteComment(Long commentId);

}
