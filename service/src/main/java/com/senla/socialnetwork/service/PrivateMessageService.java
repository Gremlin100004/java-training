package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageForCreateDto;

import java.util.Date;
import java.util.List;

public interface PrivateMessageService {
    List<PrivateMessageDto> getPrivateMessages(int firstResult, int maxResults);

    List<PrivateMessageDto> getPrivateMessagesByUser(int firstResult, int maxResults);

    List<PrivateMessageDto> getUnreadMessages(int firstResult, int maxResults);

    List<PrivateMessageDto> getMessageFilteredByPeriod(Date startPeriodDate,
                                                       Date endPeriodDate,
                                                       int firstResult,
                                                       int maxResults);

    PrivateMessageDto addMessage(PrivateMessageForCreateDto privateMessageDto);

    void updateMessage(PrivateMessageDto privateMessageDto);

    void deleteMessageByUser(Long messageId);

    void deleteMessage(Long messageId);

}
