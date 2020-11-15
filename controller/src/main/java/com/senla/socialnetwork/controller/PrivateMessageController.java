package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.exception.ControllerException;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.service.PrivateMessageService;
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

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/privateMessages")
@NoArgsConstructor
public class PrivateMessageController {
    @Autowired
    private PrivateMessageService privateMessageService;

    @GetMapping
    public List<PrivateMessageDto> getPrivateMessages(@RequestParam(required = false) Date startPeriodDate,
                                                      @RequestParam(required = false) Date endPeriodDate,
                                                      @RequestParam int firstResult,
                                                      @RequestParam int maxResults) {
        if (startPeriodDate == null && endPeriodDate == null) {
            return privateMessageService.getPrivateMessages(firstResult, maxResults);
        } else if (startPeriodDate != null && endPeriodDate != null) {
            String email = "";
//        String email = authentication.getName();
            return privateMessageService.getMessageFilteredByPeriod(
                email, startPeriodDate, endPeriodDate, firstResult, maxResults);
        } else {
            throw new ControllerException("Wrong request parameters");
        }
    }

    @PostMapping
    public PrivateMessageDto addMessage(@RequestBody PrivateMessageDto privateMessageDto) {
        return privateMessageService.addMessage(privateMessageDto);
    }

    @PutMapping
    public ClientMessageDto updateMessage(@RequestBody PrivateMessageDto privateMessageDto) {
        privateMessageService.addMessage(privateMessageDto);
        return new ClientMessageDto("Message updated successfully");
    }

    @PutMapping("/{id}/changes")
    public ClientMessageDto deleteMessageByUser(@PathVariable("id") Long messageId) {
        String email = "";
//        String email = authentication.getName();
        privateMessageService.deleteMessageByUser(email, messageId);
        return new ClientMessageDto("Message deleted successfully");
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deleteMessage(@PathVariable("id") Long messageId) {
        privateMessageService.deleteMessage(messageId);
        return new ClientMessageDto("Message deleted successfully");
    }

}
