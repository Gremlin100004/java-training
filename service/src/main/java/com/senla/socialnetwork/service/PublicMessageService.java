package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageCommentForCreateDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.PublicMessageForCreateDto;

import java.util.List;

public interface PublicMessageService {
    List<PublicMessageDto> getMessages(int firstResult, int maxResults);

    List<PublicMessageDto> getFriendsPublicMessages(int firstResult, int maxResults);

    List<PublicMessageDto> getPublicMessages(int firstResult, int maxResults);

    PublicMessageDto addMessage(PublicMessageForCreateDto publicMessageDto);

    void updateMessage(PublicMessageDto publicMessageDto);

    void deleteMessageByUser(Long messageId);

    void deleteMessage(Long messageId);

    List<PublicMessageCommentDto> getPublicMessageComments(Long publicMessageId, int firstResult, int maxResults);

    PublicMessageCommentDto addComment(Long publicMessageId, PublicMessageCommentForCreateDto publicMessageCommentDto);

}
