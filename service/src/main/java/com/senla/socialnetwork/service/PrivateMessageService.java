package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageForCreateDto;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface PrivateMessageService {
    List<PrivateMessageDto> getPrivateMessages(int firstResult, int maxResults);

    List<PrivateMessageDto> getPrivateMessages(HttpServletRequest request,
                                               int firstResult,
                                               int maxResults,
                                               SecretKey secretKey);

    List<PrivateMessageDto> getUnreadMessages(HttpServletRequest request,
                                              int firstResult,
                                              int maxResults,
                                              SecretKey secretKey);

    List<PrivateMessageDto> getMessageFilteredByPeriod(HttpServletRequest request,
                                                       Date startPeriodDate,
                                                       Date endPeriodDate,
                                                       int firstResult,
                                                       int maxResults,
                                                       SecretKey secretKey);

    PrivateMessageDto addMessage(HttpServletRequest request,
                                 PrivateMessageForCreateDto privateMessageDto,
                                 SecretKey secretKey);

    void updateMessage(HttpServletRequest request,
                       PrivateMessageDto privateMessageDto,
                       SecretKey secretKey);

    void deleteMessageByUser(HttpServletRequest request, Long messageId, SecretKey secretKey);

    void deleteMessage(Long messageId);

}
