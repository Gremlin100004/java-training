package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.util.SecretKeyUtil;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.dto.PostCommentForCreateDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.service.PostService;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/communities/posts")
@Api(tags = "Posts")
@NoArgsConstructor
public class PostController {
    public static final int OK = 200;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    public static final String RETURN_LIST_OF_POSTS_OK_MESSAGE = "Successfully retrieved list of posts";
    public static final String RETURN_LIST_OF_POST_COMMENTS_OK_MESSAGE = "Successfully retrieved list of post comments";
    public static final String UPDATE_POST_OK_MESSAGE = "Successfully updated a post";
    public static final String DELETE_POST_OK_MESSAGE = "Successfully deleted a post";
    public static final String ADD_COMMENT_OK_MESSAGE = "Successfully added a comment";
    public static final String FIRST_RESULT_DESCRIPTION = "The number of the first element of the expected list";
    public static final String MAX_RESULTS_DESCRIPTION = "Maximum number of list elements";
    public static final String FIRST_RESULT_EXAMPLE = "1";
    public static final String MAX_RESULTS_EXAMPLE = "10";
    public static final String POST_DTO_DESCRIPTION = "DTO post object";
    public static final String COMMENT_DTO_DESCRIPTION = "DTO post comment";
    public static final String POST_ID_DESCRIPTION = "Post id";
    public static final String GET_POSTS_DESCRIPTION = "This method is used to get posts by admin";
    public static final String GET_POST_COMMENTS_DESCRIPTION = "This method is used to get post comments";
    public static final String GET_POSTS_HISTORY_DESCRIPTION = "This method is used to get a list of community posts"
       + " that this user is subscribed to, called the post history";
    public static final String UPDATE_POST_DESCRIPTION = "This method is used to update a post by this user";
    public static final String DELETE_POST_DESCRIPTION = "This method is used to delete a post by admin";
    public static final String DELETE_POST_BY_USER_DESCRIPTION = "This method is used to delete a post by this user";
    public static final String ADD_COMMENT_DESCRIPTION = "This method is used to add new comment to post by this user";
    @Autowired
    PostService postService;

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    @ApiOperation(value = GET_POSTS_DESCRIPTION, response = PostDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_POSTS_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PostDto> getPosts(@ApiParam(value = FIRST_RESULT_DESCRIPTION, example = FIRST_RESULT_EXAMPLE)
                                  @RequestParam final int firstResult,
                                  @ApiParam(value = MAX_RESULTS_DESCRIPTION, example = MAX_RESULTS_EXAMPLE)
                                  @RequestParam final int maxResults) {
        return postService.getPosts(firstResult, maxResults);
    }

    @GetMapping("/history")
    @ApiOperation(value = GET_POSTS_HISTORY_DESCRIPTION, response = PostDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_POSTS_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PostDto> getPostsHistory(@ApiParam(value = FIRST_RESULT_DESCRIPTION, example = FIRST_RESULT_EXAMPLE)
                                         @RequestParam final int firstResult,
                                         @ApiParam(value = MAX_RESULTS_DESCRIPTION, example = MAX_RESULTS_EXAMPLE)
                                         @RequestParam final int maxResults,
                                         final HttpServletRequest request) {
        return postService.getPostsFromSubscribedCommunities(
            request, firstResult, maxResults, SecretKeyUtil.getSecretKey());
    }

    @PutMapping
    @ApiOperation(value = UPDATE_POST_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = UPDATE_POST_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto updatePost(@ApiParam(value = POST_DTO_DESCRIPTION)
                                       @RequestBody @Valid final PostDto postDto) {
        postService.updatePost(postDto);
        return new ClientMessageDto(UPDATE_POST_OK_MESSAGE);
    }

    @PutMapping("/{id}/changes")
    @ApiOperation(value = DELETE_POST_BY_USER_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_POST_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deletePostByUser(@ApiParam(value = POST_ID_DESCRIPTION)
                                             @PathVariable("id") final Long postId,
                                             final HttpServletRequest request) {
        postService.deletePostByUser(request, postId, SecretKeyUtil.getSecretKey());
        return new ClientMessageDto(DELETE_POST_OK_MESSAGE);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ApiOperation(value = DELETE_POST_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_POST_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deletePost(@ApiParam(value = POST_ID_DESCRIPTION) @PathVariable("id") final Long postId) {
        postService.deletePost(postId);
        return new ClientMessageDto(DELETE_POST_OK_MESSAGE);
    }

    @GetMapping("/{id}/comments")
    @ApiOperation(value = GET_POST_COMMENTS_DESCRIPTION, response = PostCommentDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_POST_COMMENTS_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PostCommentDto> getPostComments(@ApiParam(value = POST_ID_DESCRIPTION)
                                                @PathVariable("id") final Long postId,
                                                @ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                          example = FIRST_RESULT_EXAMPLE)
                                                @RequestParam final int firstResult,
                                                @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                          example = MAX_RESULTS_EXAMPLE)
                                                @RequestParam final int maxResults) {
        return postService.getPostComments(postId, firstResult, maxResults);
    }

    @PostMapping("/{id}/comments")
    @ApiOperation(value = ADD_COMMENT_DESCRIPTION, response = PostCommentDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = ADD_COMMENT_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public PostCommentDto addComment(@ApiParam(value = POST_ID_DESCRIPTION)
                                     @PathVariable("id") final Long postId,
                                     @ApiParam(value = COMMENT_DTO_DESCRIPTION)
                                     @RequestBody @Valid final PostCommentForCreateDto postCommentDto,
                                     final HttpServletRequest request) {
        return postService.addComment(request, postId, postCommentDto, SecretKeyUtil.getSecretKey());
    }

}
