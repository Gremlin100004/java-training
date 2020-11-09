package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PublicMessageDto;

import java.util.List;

public interface PublicMessageService {
    List<PublicMessageDto> getFriendsMessages(String email, int firstResult, int maxResults);

}
