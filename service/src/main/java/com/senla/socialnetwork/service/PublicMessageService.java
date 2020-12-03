package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageCommentForCreateDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.PublicMessageForCreateDto;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicMessageService {
    List<PublicMessageDto> getMessages(int firstResult, int maxResults);

    List<PublicMessageDto> getFriendsPublicMessages(HttpServletRequest request,
                                                    int firstResult,
                                                    int maxResults,
                                                    SecretKey secretKey);

    List<PublicMessageDto> getPublicMessages(HttpServletRequest request,
                                             int firstResult,
                                             int maxResults,
                                             SecretKey secretKey);

    PublicMessageDto addMessage(HttpServletRequest request,
                                PublicMessageForCreateDto publicMessageDto,
                                SecretKey secretKey);

    void updateMessage(HttpServletRequest request,
                       PublicMessageDto publicMessageDto,
                       SecretKey secretKey);

    void deleteMessageByUser(Long messageId, HttpServletRequest request, SecretKey secretKey);

    void deleteMessage(Long messageId);

    List<PublicMessageCommentDto> getPublicMessageComments(Long publicMessageId,
                                                           int firstResult,
                                                           int maxResults);

    PublicMessageCommentDto addComment(HttpServletRequest request,
                                       Long publicMessageId,
                                       PublicMessageCommentForCreateDto publicMessageCommentDto,
                                       SecretKey secretKey);

}
