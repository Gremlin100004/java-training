package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.util.ValidationUtil;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageCommentForCreateDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.PublicMessageForCreateDto;
import com.senla.socialnetwork.service.PublicMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/publicMessages")
@Api(tags = "Public Messages")
@NoArgsConstructor
public class PublicMessageController {
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    public static final String RETURN_LIST_OF_PUBLIC_MESSAGES_OK_MESSAGE = "Successfully retrieved list of "
       + "public messages";
    public static final String RETURN_PUBLIC_MESSAGE_OK_MESSAGE = "Successfully retrieved a public message";
    public static final String UPDATE_MESSAGE_OK_MESSAGE = "Successfully updated a public message";
    public static final String DELETE_MESSAGE_OK_MESSAGE = "Successfully deleted a public message";
    public static final String RETURN_LIST_OF_PUBLIC_MESSAGE_POSTS_OK_MESSAGE = "Successfully retrieved list of "
       + "public message comments";
    public static final String RETURN_COMMENT_OK_MESSAGE = "Successfully retrieved a public message comment";
    public static final String FIRST_RESULT_DESCRIPTION = "The number of the first element of the expected list";
    public static final String MAX_RESULTS_DESCRIPTION = "Maximum number of list elements";
    public static final String FIRST_RESULT_EXAMPLE = "1";
    public static final String MAX_RESULTS_EXAMPLE = "10";
    public static final String PUBLIC_MESSAGE_DTO_DESCRIPTION = "DTO public message object";
    public static final String COMMENTS_DTO_DESCRIPTION = "DTO public message comment";
    public static final String PUBLIC_MESSAGE_ID_DESCRIPTION = "Public message id";
    public static final String PUBLIC_MESSAGE_ID_EXAMPLE = "1";
    public static final String PUBLIC_MESSAGE_TO_DELETE_ID_EXAMPLE = "9";
    public static final String GET_PUBLIC_MESSAGES_ALL_DESCRIPTION = "This method is used to get public messages "
       + "by admin";
    public static final String GET_PUBLIC_MESSAGES_DESCRIPTION = "This method is used to get public messages of a "
       + "given user";
    public static final String ADD_MESSAGE_DESCRIPTION = "This method is used to add new public message of a "
       + "given user";
    public static final String UPDATE_MESSAGE_DESCRIPTION = "This method is used to update the public message of a "
       + "given user";
    public static final String DELETE_MESSAGE_BY_USER_DESCRIPTION = "This method is used to delete the public "
       + "message of a given user";
    public static final String DELETE_MESSAGE_DESCRIPTION = "This method is used to delete a record from the "
       + "database by the admin";
    public static final String GET_PUBLIC_MESSAGE_COMMENTS_DESCRIPTION = "This method is used to get public message"
       + " comments";
    public static final String ADD_COMMENTS_DESCRIPTION = "This method is used to add new public message comment "
       + "by this user";
    @Autowired
    private PublicMessageService publicMessageService;

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin")
    @ApiOperation(value = GET_PUBLIC_MESSAGES_ALL_DESCRIPTION, response = PublicMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_PUBLIC_MESSAGES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PublicMessageDto> getMessages(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                        example = FIRST_RESULT_EXAMPLE)
                                              @RequestParam final int firstResult,
                                              @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                        example = MAX_RESULTS_EXAMPLE)
                                              @RequestParam final int maxResults) {
        return publicMessageService.getMessages(firstResult, maxResults);
    }

    @GetMapping
    @ApiOperation(value = GET_PUBLIC_MESSAGES_DESCRIPTION, response = PublicMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_PUBLIC_MESSAGES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PublicMessageDto> getPublicMessages(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                              example = FIRST_RESULT_EXAMPLE)
                                                    @RequestParam final int firstResult,
                                                    @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                              example = MAX_RESULTS_EXAMPLE)
                                                    @RequestParam final int maxResults) {
        return publicMessageService.getPublicMessages(firstResult, maxResults);
    }

    @PostMapping
    @ApiOperation(value = ADD_MESSAGE_DESCRIPTION, response = PublicMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = CREATED, message = RETURN_PUBLIC_MESSAGE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public PublicMessageDto addMessage(@ApiParam(value = PUBLIC_MESSAGE_DTO_DESCRIPTION)
                                       @RequestBody final PublicMessageForCreateDto publicMessageDto) {
        ValidationUtil.validate(publicMessageDto);
        return publicMessageService.addMessage(publicMessageDto);
    }

    @PutMapping
    @ApiOperation(value = UPDATE_MESSAGE_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = UPDATE_MESSAGE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto updateMessage(@ApiParam(value = PUBLIC_MESSAGE_DTO_DESCRIPTION)
                                          @RequestBody final PublicMessageDto publicMessageDto) {
        ValidationUtil.validate(publicMessageDto);
        publicMessageService.updateMessage(publicMessageDto);
        return new ClientMessageDto(UPDATE_MESSAGE_OK_MESSAGE);
    }

    @PutMapping("/{id}/changes")
    @ApiOperation(value = DELETE_MESSAGE_BY_USER_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_MESSAGE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteMessageByUser(@ApiParam(value = PUBLIC_MESSAGE_ID_DESCRIPTION,
                                                          example = PUBLIC_MESSAGE_TO_DELETE_ID_EXAMPLE)
                                                @PathVariable("id") final Long messageId) {
        publicMessageService.deleteMessageByUser(messageId);
        return new ClientMessageDto(DELETE_MESSAGE_OK_MESSAGE);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ApiOperation(value = DELETE_MESSAGE_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_MESSAGE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteMessage(@ApiParam(value = PUBLIC_MESSAGE_ID_DESCRIPTION,
                                                    example = PUBLIC_MESSAGE_TO_DELETE_ID_EXAMPLE)
                                          @PathVariable("id") final Long messageId) {
        publicMessageService.deleteMessage(messageId);
        return new ClientMessageDto(DELETE_MESSAGE_OK_MESSAGE);
    }

    @GetMapping("/{id}/comments")
    @ApiOperation(value = GET_PUBLIC_MESSAGE_COMMENTS_DESCRIPTION, response = PublicMessageCommentDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_PUBLIC_MESSAGE_POSTS_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PublicMessageCommentDto> getPublicMessageComments(@ApiParam(value = PUBLIC_MESSAGE_ID_DESCRIPTION,
                                                                            example = PUBLIC_MESSAGE_ID_EXAMPLE)
                                                                  @PathVariable("id") final Long publicMessageId,
                                                                  @ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                                            example = FIRST_RESULT_EXAMPLE)
                                                                  @RequestParam final int firstResult,
                                                                  @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                                            example = MAX_RESULTS_EXAMPLE)
                                                                  @RequestParam final int maxResults) {
        return publicMessageService.getPublicMessageComments(publicMessageId, firstResult, maxResults);
    }

    @PostMapping("/{id}/comments")
    @ApiOperation(value = ADD_COMMENTS_DESCRIPTION, response = PublicMessageCommentDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = CREATED, message = RETURN_COMMENT_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public PublicMessageCommentDto addComment(@ApiParam(value = PUBLIC_MESSAGE_ID_DESCRIPTION,
                                                        example = PUBLIC_MESSAGE_ID_EXAMPLE)
                                              @PathVariable("id") final Long publicMessageId,
                                              @ApiParam(value = COMMENTS_DTO_DESCRIPTION)
                                              @RequestBody @Valid
                                              final PublicMessageCommentForCreateDto publicMessageCommentDto) {
        ValidationUtil.validate(publicMessageCommentDto);
        return publicMessageService.addComment(publicMessageId, publicMessageCommentDto);
    }

}
