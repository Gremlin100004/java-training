package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.service.PublicMessageService;
import io.swagger.annotations.Api;
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
@Api(tags = "Public Messages")
@NoArgsConstructor
public class PublicMessageController {
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String BAD_REQUEST_MESSAGE = "Successfully retrieved list";
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
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
