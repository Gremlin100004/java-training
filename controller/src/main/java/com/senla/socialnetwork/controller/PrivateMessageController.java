package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.exception.ControllerException;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.service.PrivateMessageService;
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

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/privateMessages")
@Api(tags = "Private Messages")
@NoArgsConstructor
public class PrivateMessageController {
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String BAD_REQUEST_MESSAGE = "Successfully retrieved list";
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    @Autowired
    private PrivateMessageService privateMessageService;

    @GetMapping
    public List<PrivateMessageDto> getPrivateMessages(@RequestParam(required = false) Date startPeriodDate,
                                                      @RequestParam(required = false) Date endPeriodDate,
                                                      @RequestParam int firstResult,
                                                      @RequestParam int maxResults,
                                                      HttpServletRequest request) {
        if (startPeriodDate == null && endPeriodDate == null) {
            return privateMessageService.getPrivateMessages(firstResult, maxResults);
        } else if (startPeriodDate != null && endPeriodDate != null) {
            return privateMessageService.getMessageFilteredByPeriod(
                request, startPeriodDate, endPeriodDate, firstResult, maxResults);
        } else {
            throw new ControllerException("Wrong request parameters");
        }
    }

    @PostMapping
    public PrivateMessageDto addMessage(@RequestBody PrivateMessageDto privateMessageDto,
                                        HttpServletRequest request) {
        return privateMessageService.addMessage(request, privateMessageDto);
    }

    @PutMapping
    public ClientMessageDto updateMessage(@RequestBody PrivateMessageDto privateMessageDto,
                                          HttpServletRequest request) {
        privateMessageService.updateMessage(request, privateMessageDto);
        return new ClientMessageDto("Message updated successfully");
    }

    @PutMapping("/{id}/changes")
    public ClientMessageDto deleteMessageByUser(@PathVariable("id") Long messageId, HttpServletRequest request) {
        privateMessageService.deleteMessageByUser(request, messageId);
        return new ClientMessageDto("Message deleted successfully");
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deleteMessage(@PathVariable("id") Long messageId) {
        privateMessageService.deleteMessage(messageId);
        return new ClientMessageDto("Message deleted successfully");
    }

}
