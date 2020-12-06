package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.SigningKey;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageForCreateDto;
import com.senla.socialnetwork.service.PrivateMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/privateMessages")
@Api(tags = "Private Messages")
@NoArgsConstructor
public class PrivateMessageController {
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    public static final String RETURN_LIST_OF_PRIVATE_MESSAGES_OK_MESSAGE = "Successfully retrieved list of "
       + "private messages";
    public static final String RETURN_PRIVATE_MESSAGE_OK_MESSAGE = "Successfully retrieved a private message";
    public static final String UPDATE_PRIVATE_MESSAGE_OK_MESSAGE = "Successfully updated a private message";
    public static final String DELETE_PRIVATE_MESSAGE_OK_MESSAGE = "Successfully deleted a private message";
    public static final String FIRST_RESULT_DESCRIPTION = "The number of the first element of the expected list";
    public static final String MAX_RESULTS_DESCRIPTION = "Maximum number of list elements";
    public static final String FIRST_RESULT_EXAMPLE = "1";
    public static final String MAX_RESULTS_EXAMPLE = "10";
    public static final String PRIVATE_MESSAGE_DTO_DESCRIPTION = "DTO private message";
    public static final String PRIVATE_MESSAGE_ID_DESCRIPTION = "Private message id";
    public static final String START_PERIOD_DATE_DESCRIPTION = "Parameter with start date";
    public static final String END_PERIOD_DATE_DESCRIPTION = "Parameter with end date";
    public static final String START_PERIOD_DATE_EXAMPLE = "2020-11-01 12:04";
    public static final String END_PERIOD_DATE_EXAMPLE = "2020-11-11 12:05";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String GET_PRIVATE_MESSAGES_DESCRIPTION = "This method is used to get private messages by admin";
    public static final String GET_PRIVATE_BY_PERIOD_MESSAGES_DESCRIPTION = "This method is used to get private "
       + "messages by this user. Can be filtered by date and time.";
    public static final String GET_UNREAD_MESSAGES_DESCRIPTION = "This method is used to get unread messages";
    public static final String ADD_PRIVATE_MESSAGE_DESCRIPTION = "This method is used to add new private message "
       + "by this user";
    public static final String UPDATE_PRIVATE_MESSAGE_DESCRIPTION = "This method is used to update private message "
       + "by this user";
    public static final String DELETE_PRIVATE_MESSAGE_BY_USER_DESCRIPTION = "This method is used to delete private "
       + "message by this user";
    public static final String DELETE_PRIVATE_MESSAGE_DESCRIPTION = "This method is used to delete private message "
       + "by admin";
    @Autowired
    private PrivateMessageService privateMessageService;
    @Autowired
    private SigningKey signingKey;

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin")
    @ApiOperation(value = GET_PRIVATE_MESSAGES_DESCRIPTION, response = PrivateMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_PRIVATE_MESSAGES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PrivateMessageDto> getPrivateMessages(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                                example = FIRST_RESULT_EXAMPLE)
                                                      @RequestParam final int firstResult,
                                                      @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                                example = MAX_RESULTS_EXAMPLE)
                                                      @RequestParam final int maxResults) {
        return privateMessageService.getPrivateMessages(firstResult, maxResults);

    }

    @GetMapping("/unreadMessages")
    @ApiOperation(value = GET_UNREAD_MESSAGES_DESCRIPTION, response = PrivateMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_PRIVATE_MESSAGES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PrivateMessageDto> getUnreadMessages(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                               example = FIRST_RESULT_EXAMPLE)
                                                     @RequestParam final int firstResult,
                                                     @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                               example = MAX_RESULTS_EXAMPLE)
                                                     @RequestParam final int maxResults,
                                                     final HttpServletRequest request) {
        return privateMessageService.getUnreadMessages(request, firstResult, maxResults, signingKey.getSecretKey());
    }

    @GetMapping
    @ApiOperation(value = GET_PRIVATE_BY_PERIOD_MESSAGES_DESCRIPTION, response = PrivateMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_PRIVATE_MESSAGES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PrivateMessageDto> getPrivateMessages(@ApiParam(value = START_PERIOD_DATE_DESCRIPTION,
                                                                example = START_PERIOD_DATE_EXAMPLE)
                                                      @DateTimeFormat(pattern = DATE_FORMAT)
                                                      @RequestParam(required = false)
                                                      final Date startPeriodDate,
                                                      @ApiParam(value = END_PERIOD_DATE_DESCRIPTION,
                                                                example = END_PERIOD_DATE_EXAMPLE)
                                                      @DateTimeFormat(pattern = DATE_FORMAT)
                                                      @RequestParam(required = false)
                                                      final Date endPeriodDate,
                                                      @ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                                example = FIRST_RESULT_EXAMPLE)
                                                      @RequestParam final int firstResult,
                                                      @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                                example = MAX_RESULTS_EXAMPLE)
                                                      @RequestParam final int maxResults,
                                                      final HttpServletRequest request) {
        if (startPeriodDate != null && endPeriodDate != null) {
            return privateMessageService.getMessageFilteredByPeriod(
                request, startPeriodDate, endPeriodDate, firstResult, maxResults, signingKey.getSecretKey());
        } else {
            return privateMessageService.getPrivateMessages(
                request, firstResult, maxResults, signingKey.getSecretKey());
        }
    }

    @PostMapping
    @ApiOperation(value = ADD_PRIVATE_MESSAGE_DESCRIPTION, response = PrivateMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = CREATED, message = RETURN_PRIVATE_MESSAGE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public PrivateMessageDto addMessage(@ApiParam(value = PRIVATE_MESSAGE_DTO_DESCRIPTION)
                                        @RequestBody @Valid final PrivateMessageForCreateDto privateMessageDto,
                                        final HttpServletRequest request) {
        return privateMessageService.addMessage(request, privateMessageDto, signingKey.getSecretKey());
    }

    @PutMapping
    @ApiOperation(value = UPDATE_PRIVATE_MESSAGE_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = UPDATE_PRIVATE_MESSAGE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto updateMessage(@ApiParam(value = PRIVATE_MESSAGE_DTO_DESCRIPTION)
                                          @RequestBody @Valid final PrivateMessageDto privateMessageDto,
                                          final HttpServletRequest request) {
        privateMessageService.updateMessage(request, privateMessageDto, signingKey.getSecretKey());
        return new ClientMessageDto(UPDATE_PRIVATE_MESSAGE_OK_MESSAGE);
    }

    @PutMapping("/{id}/changes")
    @ApiOperation(value = DELETE_PRIVATE_MESSAGE_BY_USER_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_PRIVATE_MESSAGE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteMessageByUser(@ApiParam(value = PRIVATE_MESSAGE_ID_DESCRIPTION)
                                                @PathVariable("id") final Long messageId,
                                                final HttpServletRequest request) {
        privateMessageService.deleteMessageByUser(request, messageId, signingKey.getSecretKey());
        return new ClientMessageDto(DELETE_PRIVATE_MESSAGE_OK_MESSAGE);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ApiOperation(value = DELETE_PRIVATE_MESSAGE_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_PRIVATE_MESSAGE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteMessage(@ApiParam(value = PRIVATE_MESSAGE_ID_DESCRIPTION)
                                          @PathVariable("id") final Long messageId) {
        privateMessageService.deleteMessage(messageId);
        return new ClientMessageDto(DELETE_PRIVATE_MESSAGE_OK_MESSAGE);
    }

}
