package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.service.PublicMessageService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/publicMessages")
@NoArgsConstructor
public class PublicMessageController {
    @Autowired
    private PublicMessageService publicMessageService;

    @GetMapping
    public List<PublicMessageDto> getMessages(@RequestParam int firstResult, @RequestParam int maxResults) {
        return publicMessageService.getMessages(firstResult, maxResults);
    }

    @PostMapping
    public PublicMessageDto addMessage(@RequestBody PublicMessageDto publicMessageDto) {
        return publicMessageService.addMessage(publicMessageDto);
    }

    @PutMapping
    public ClientMessageDto updateMessage(@RequestBody PublicMessageDto publicMessageDto) {
        publicMessageService.updateMessage(publicMessageDto);
        return new ClientMessageDto("Message updated successfully");
    }

    @PutMapping("/{id}/changes")
    public ClientMessageDto deleteMessageByUser(@PathVariable("id") Long messageId) {
        String email = "";
//        String email = authentication.getName();
        publicMessageService.deleteMessageByUser(email, messageId);
        return new ClientMessageDto("Message deleted successfully");
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deleteMessage(@PathVariable("id") Long messageId) {
        publicMessageService.deleteMessage(messageId);
        return new ClientMessageDto("Message deleted successfully");
    }

    @GetMapping("/{id}/comments")
    public List<PublicMessageCommentDto> getPublicMessageComments(@PathVariable("id") Long publicMessageId,
                                                                  @RequestParam int firstResult,
                                                                  @RequestParam int maxResults) {
        return publicMessageService.getPublicMessageComments(publicMessageId, firstResult, maxResults);
    }

}