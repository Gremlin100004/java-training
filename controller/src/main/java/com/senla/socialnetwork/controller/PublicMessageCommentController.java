package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.service.PublicMessageCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/publicMessages/comments")
@Api(tags = "Public Messages Comments")
@NoArgsConstructor
public class PublicMessageCommentController {
    public static final int OK = 200;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    public static final String RETURN_LIST_OF_MESSAGES_OK_MESSAGE = "Successfully retrieved list of public "
       + "messages comments";
    public static final String UPDATE_MESSAGE_OK_MESSAGE = "Successfully updated a public message comment";
    public static final String DELETE_MESSAGE_OK_MESSAGE = "Successfully deleted a public message comment";
    public static final String FIRST_RESULT_DESCRIPTION = "The number of the first element of the expected list";
    public static final String MAX_RESULTS_DESCRIPTION = "Maximum number of list elements";
    public static final String FIRST_RESULT_EXAMPLE = "1";
    public static final String MAX_RESULTS_EXAMPLE = "10";
    public static final String COMMENTS_DTO_DESCRIPTION = "DTO public message comment";
    public static final String COMMENTS_ID_DESCRIPTION = "Public message comment id";
    public static final String COMMENTS_ID_EXAMPLE = "1";
    public static final String GET_COMMENTS_DESCRIPTION = "This method is used to get public messages comments by admin";
    public static final String UPDATE_COMMENTS_DESCRIPTION = "This method is used to update public message comment "
       + "by this user";
    public static final String DELETE_COMMENTS_BY_USER_DESCRIPTION = "This method is used to delete public message "
       + "comment by this user";
    public static final String DELETE_COMMENTS_DESCRIPTION = "This method is used to delete public message comment "
       + "by admin";
    @Autowired
    private PublicMessageCommentService publicMessageCommentService;
    @Value("${com.senla.socialnetwork.JwtUtil.secret-key:qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq}")
    private String secretKey;

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    @ApiOperation(value = GET_COMMENTS_DESCRIPTION, response = PublicMessageCommentDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_MESSAGES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PublicMessageCommentDto> getComments(@ApiParam(value = FIRST_RESULT_DESCRIPTION, example = FIRST_RESULT_EXAMPLE)
                                                     @RequestParam int firstResult,
                                                     @ApiParam(value = MAX_RESULTS_DESCRIPTION, example = MAX_RESULTS_EXAMPLE)
                                                     @RequestParam int maxResults) {
        return publicMessageCommentService.getComments(firstResult, maxResults);
    }

    @PutMapping
    @ApiOperation(value = UPDATE_COMMENTS_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = UPDATE_MESSAGE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto updateComment(@ApiParam(value = COMMENTS_DTO_DESCRIPTION)
                                          @RequestBody @Valid PublicMessageCommentDto publicMessageCommentDto,
                                          HttpServletRequest request) {
        publicMessageCommentService.updateComment(request, publicMessageCommentDto);
        return new ClientMessageDto(UPDATE_MESSAGE_OK_MESSAGE);
    }

    @PutMapping("/{id}/changes")
    @ApiOperation(value = DELETE_COMMENTS_BY_USER_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_MESSAGE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteCommentByUser(@ApiParam(value = COMMENTS_ID_DESCRIPTION,
                                                          example = COMMENTS_ID_EXAMPLE)
                                                @PathVariable("id") Long commentId,
                                                HttpServletRequest request) {
         publicMessageCommentService.deleteCommentByUser(request, commentId);
        return new ClientMessageDto(DELETE_MESSAGE_OK_MESSAGE);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ApiOperation(value = DELETE_COMMENTS_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_MESSAGE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteComment(@ApiParam(value = COMMENTS_ID_DESCRIPTION,
                                                    example = COMMENTS_ID_EXAMPLE)
                                          @PathVariable("id") Long commentId) {
        publicMessageCommentService.deleteComment(commentId);
        return new ClientMessageDto(DELETE_MESSAGE_OK_MESSAGE);
    }

}
