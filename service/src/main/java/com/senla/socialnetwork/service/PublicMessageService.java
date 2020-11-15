package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageDto;

import java.util.List;

public interface PublicMessageService {
    List<PublicMessageDto> getMessages(int firstResult, int maxResults);

    PublicMessageDto addMessage(PublicMessageDto publicMessageDto);

    void updateMessage(PublicMessageDto publicMessageDto);

    void deleteMessageByUser(String email, Long messageId);

    void deleteMessage(Long messageId);

    List<PublicMessageCommentDto> getPublicMessageComments(Long publicMessageId, int firstResult, int maxResults);

}
