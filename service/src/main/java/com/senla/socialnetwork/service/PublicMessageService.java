package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicMessageService {
    List<PublicMessageDto> getMessages(int firstResult, int maxResults);

    PublicMessageDto addMessage(HttpServletRequest request, PublicMessageDto publicMessageDto);

    void updateMessage(HttpServletRequest request, PublicMessageDto publicMessageDto);

    void deleteMessageByUser(Long messageId, HttpServletRequest request);

    void deleteMessage(Long messageId);

    List<PublicMessageCommentDto> getPublicMessageComments(Long publicMessageId, int firstResult, int maxResults);

}
