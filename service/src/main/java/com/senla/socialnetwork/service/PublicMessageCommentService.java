package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PublicMessageCommentService {
    @Transactional
    List<PublicMessageCommentDto> Comments();

    List<PublicMessageCommentDto> getPublicMessageComments(Long publicMessageId);

    PublicMessageCommentDto addComment(PublicMessageCommentDto publicMessageCommentDto);

    void updateComment(PublicMessageCommentDto publicMessageCommentDto);

    void deleteCommentByUser(String email, Long messageId);

    void deleteComment(Long commentId);

}
