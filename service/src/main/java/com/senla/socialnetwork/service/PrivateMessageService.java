package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PrivateMessageDto;

import java.util.Date;
import java.util.List;

public interface PrivateMessageService {
    List<PrivateMessageDto> getPrivateMessages(int firstResult, int maxResults);

    List<PrivateMessageDto> getMessageFilteredByPeriod(String email,
                                                       Date startPeriodDate,
                                                       Date endPeriodDate,
                                                       int firstResult,
                                                       int maxResults);

    PrivateMessageDto addMessage(PrivateMessageDto privateMessageDto);

    void updateMessage(PrivateMessageDto privateMessageDto);

    void deleteMessageByUser(String email, Long messageId);

    void deleteMessage(Long messageId);

}
