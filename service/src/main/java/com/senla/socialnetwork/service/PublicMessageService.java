package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PublicMessageDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PublicMessageService {
    @Transactional
    List<PublicMessageDto> getMessages(int firstResult, int maxResults);

    List<PublicMessageDto> getFriendsMessages(String email, int firstResult, int maxResults);

    List<PublicMessageDto> getUserProfileMessages(String email, int firstResult, int maxResults);

    PublicMessageDto addMessage(PublicMessageDto publicMessageDto);

    void updateMessage(PublicMessageDto publicMessageDto);

    void deleteMessageByUser(String email, Long messageId);

    void deleteMessage(Long messageId);

}
