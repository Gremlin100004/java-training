package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageForCreateDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface PrivateMessageService {
    List<PrivateMessageDto> getPrivateMessages(int firstResult, int maxResults);

    List<PrivateMessageDto> getMessageFilteredByPeriod(HttpServletRequest request,
                                                       Date startPeriodDate,
                                                       Date endPeriodDate,
                                                       int firstResult,
                                                       int maxResults);

    PrivateMessageDto addMessage(HttpServletRequest request, PrivateMessageForCreateDto privateMessageDto);

    void updateMessage(HttpServletRequest request, PrivateMessageDto privateMessageDto);

    void deleteMessageByUser(HttpServletRequest request, Long messageId);

    void deleteMessage(Long messageId);

}
