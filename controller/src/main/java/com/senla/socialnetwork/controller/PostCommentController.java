package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.SigningKey;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.service.PostCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/communities/posts/comments")
@Api(tags = "Posts Comments")
@NoArgsConstructor
public class PostCommentController {
    public static final int OK = 200;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    public static final String RETURN_LIST_OF_COMMENTS_OK_MESSAGE = "Successfully retrieved list of comments";
    public static final String UPDATE_COMMENT_OK_MESSAGE = "Successfully updated a comment";
    public static final String DELETE_COMMENT_OK_MESSAGE = "Successfully deleted a comment";
    public static final String FIRST_RESULT_DESCRIPTION = "The number of the first element of the expected list";
    public static final String MAX_RESULTS_DESCRIPTION = "Maximum number of list elements";
    public static final String FIRST_RESULT_EXAMPLE = "1";
    public static final String MAX_RESULTS_EXAMPLE = "10";
    public static final String COMMENT_DTO_DESCRIPTION = "DTO post comment";
    public static final String COMMENT_ID_DESCRIPTION = "Post comment id";
    public static final String GET_COMMENTS_DESCRIPTION = "This method is used to get comments by admin";
    public static final String UPDATE_COMMENT_DESCRIPTION = "This method is used to update comment by this user";
    public static final String DELETE_COMMENT_BY_USER_DESCRIPTION = "This method is used to delete comment by this user";
    public static final String DELETE_COMMENT_DESCRIPTION = "This method is used to delete comment by admin";
    @Autowired
    private PostCommentService postCommentService;
    @Autowired
    private SigningKey signingKey;

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    @ApiOperation(value = GET_COMMENTS_DESCRIPTION, response = PostCommentDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_COMMENTS_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PostCommentDto> getComments(@ApiParam(value = FIRST_RESULT_DESCRIPTION, example = FIRST_RESULT_EXAMPLE)
                                            @RequestParam final int firstResult,
                                            @ApiParam(value = MAX_RESULTS_DESCRIPTION, example = MAX_RESULTS_EXAMPLE)
                                            @RequestParam final int maxResults) {
        return postCommentService.getComments(firstResult, maxResults);
    }

    @PutMapping
    @ApiOperation(value = UPDATE_COMMENT_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = UPDATE_COMMENT_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto updateComment(@ApiParam(value = COMMENT_DTO_DESCRIPTION)
                                          @RequestBody @Valid final PostCommentDto postCommentDto) {
        postCommentService.updateComment(postCommentDto);
        return new ClientMessageDto(UPDATE_COMMENT_OK_MESSAGE);
    }

    @PutMapping("/{id}/changes")
    @ApiOperation(value = DELETE_COMMENT_BY_USER_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_COMMENT_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteCommentByUser(@ApiParam(value = COMMENT_ID_DESCRIPTION)
                                                @PathVariable("id") final Long commentId) {
        postCommentService.deleteCommentByUser(commentId);
        return new ClientMessageDto(DELETE_COMMENT_OK_MESSAGE);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ApiOperation(value = DELETE_COMMENT_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_COMMENT_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteComment(@ApiParam(value = COMMENT_ID_DESCRIPTION)
                                          @PathVariable("id") final Long commentId) {
        postCommentService.deleteComment(commentId);
        return new ClientMessageDto(DELETE_COMMENT_OK_MESSAGE);
    }

}
