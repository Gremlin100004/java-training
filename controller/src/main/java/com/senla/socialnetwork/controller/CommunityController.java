package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.exception.ControllerException;
import com.senla.socialnetwork.controller.config.SigningKey;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.dto.CommunityForCreateDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.dto.PostForCreationDto;
import com.senla.socialnetwork.service.CommunityService;
import com.senla.socialnetwork.service.enumaration.CommunitySortParameter;
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
@RequestMapping("/communities")
@Api(tags = "Communities")
@NoArgsConstructor
public class CommunityController {
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String RETURN_LIST_OF_COMMUNITIES_OK_MESSAGE = "Successfully retrieved list of communities";
    public static final String RETURN_LIST_OF_POSTS_OK_MESSAGE = "Successfully retrieved list of posts";
    public static final String SUBSCRIBE_TO_COMMUNITY_OK_MESSAGE = "Subscription to community was successful";
    public static final String UNSUBSCRIBE_TO_COMMUNITY_OK_MESSAGE = "Community unsubscription was successful";
    public static final String ADD_COMMUNITY_OK_MESSAGE = "Successful community addition";
    public static final String UPDATE_COMMUNITY_OK_MESSAGE = "Successful community update";
    public static final String DELETE_COMMUNITY_OK_MESSAGE = "Community deleted successfully";
    public static final String ADD_POST_TO_COMMUNITY_OK_MESSAGE = "Post added successfully";
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    public static final String GET_ALL_COMMUNITIES_DESCRIPTION = "This method is used to get absolutely all "
       + "communities by admin.";
    public static final String GET_COMMUNITIES_DESCRIPTION = "This method is used to get communities that have not "
       + "been deleted. Communities can be sort or filtered";
    public static final String GET_OWN_COMMUNITIES_DESCRIPTION = "This method is used to get the communities of the "
       + "user of his account";
    public static final String GET_SUBSCRIBED_COMMUNITIES_DESCRIPTION = "This method is used to get the "
       + "communities that the user has subscribed to";
    public static final String GET_COMMUNITY_POSTS_DESCRIPTION = "This method is used to get a list of community posts";
    public static final String SUBSCRIBE_TO_COMMUNITY_DESCRIPTION = "This method is used to subscribe to "
       + "the community";
    public static final String UNSUBSCRIBE_FROM_COMMUNITY_DESCRIPTION = "This method is used to unsubscribe "
       + "from the community";
    public static final String ADD_COMMUNITY_DESCRIPTION = "This method is used to add a new community of the "
       + "given account";
    public static final String UPDATE_COMMUNITY_DESCRIPTION = "This method is used to update a new community of "
       + "the given account";
    public static final String DELETE_COMMUNITY_BY_USER_DESCRIPTION = "This method is used to delete the "
       + "community of the given account(deletion status is set)";
    public static final String DELETE_COMMUNITY_DESCRIPTION = "This method is used to delete a record from the "
       + "database by the admin";
    public static final String ADD_POST_TO_COMMUNITY_DESCRIPTION = "This method is used to add a post to the "
       + "community of the given account";
    public static final String FIRST_RESULT_DESCRIPTION = "The number of the first element of the expected list";
    public static final String MAX_RESULTS_DESCRIPTION = "Maximum number of list elements";
    public static final String FIRST_RESULT_EXAMPLE = "1";
    public static final String MAX_RESULTS_EXAMPLE = "10";
    public static final String SORT_PARAMETER_DESCRIPTION = "Parameter to select sorting";
    public static final String COMMUNITY_TYPE_DESCRIPTION = "The type of community to filter the list by";
    public static final String COMMUNITY_ID_DESCRIPTION = " Community id";
    public static final String COMMUNITY_ID_FOR_DELETE = "5";
    public static final String COMMUNITY_ID_FOR_ADD_POST = "1";
    public static final String COMMUNITY_ID_FOR_GET_POSTS = "2";
    public static final String COMMUNITY_ID_FOR_SUBSCRIBE_POSTS = "3";
    public static final String COMMUNITY_DTO_DESCRIPTION = " DTO community object";
    public static final String POST_DTO_DESCRIPTION = " DTO community post object";
    @Autowired
    private CommunityService communityService;
    @Autowired
    private SigningKey signingKey;

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/all")
    @ApiOperation(value = GET_ALL_COMMUNITIES_DESCRIPTION, response = CommunityDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_COMMUNITIES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<CommunityDto> getAllCommunities(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                          example = FIRST_RESULT_EXAMPLE)
                                                @RequestParam final int firstResult,
                                                @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                          example = MAX_RESULTS_EXAMPLE)
                                                @RequestParam final int maxResults) {
        return communityService.getAllCommunities(firstResult, maxResults);
    }

    @GetMapping
    @ApiOperation(value = GET_COMMUNITIES_DESCRIPTION, response = CommunityDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_COMMUNITIES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<CommunityDto> getCommunities(@ApiParam(value = SORT_PARAMETER_DESCRIPTION)
                                             @RequestParam(required = false) final CommunitySortParameter sortParameter,
                                             @ApiParam(value = COMMUNITY_TYPE_DESCRIPTION)
                                             @RequestParam(required = false) final CommunityType communityType,
                                             @ApiParam(value = FIRST_RESULT_DESCRIPTION, example = FIRST_RESULT_EXAMPLE)
                                             @RequestParam final int firstResult,
                                             @ApiParam(value = MAX_RESULTS_DESCRIPTION, example = MAX_RESULTS_EXAMPLE)
                                             @RequestParam final int maxResults) {
        if (sortParameter == null && communityType == null) {
            return communityService.getCommunities(firstResult, maxResults);
        } else if (sortParameter == CommunitySortParameter.NUMBER_OF_SUBSCRIBERS && communityType == null) {
            return communityService.getCommunitiesSortiedByNumberOfSubscribers(firstResult, maxResults);
        } else if (sortParameter == null) {
            return communityService.getCommunitiesFilteredByType(communityType, firstResult, maxResults);
        } else {
            throw new ControllerException("Wrong request parameters");
        }
    }

    @GetMapping("/own")
    @ApiOperation(value = GET_OWN_COMMUNITIES_DESCRIPTION, response = CommunityDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_COMMUNITIES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<CommunityDto> getOwnCommunities(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                          example = FIRST_RESULT_EXAMPLE)
                                                @RequestParam final int firstResult,
                                                @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                          example = MAX_RESULTS_EXAMPLE)
                                                @RequestParam final int maxResults,
                                                final HttpServletRequest request) {
        return communityService.getOwnCommunities(request, firstResult, maxResults, signingKey.getSecretKey());
    }

    @GetMapping("/{id}/posts")
    @ApiOperation(value = GET_COMMUNITY_POSTS_DESCRIPTION, response = PostDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_POSTS_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PostDto> getCommunityPosts(@ApiParam(value = COMMUNITY_ID_DESCRIPTION,
                                                     example = COMMUNITY_ID_FOR_GET_POSTS)
                                           @PathVariable("id") Long communityId,
                                           @ApiParam(value = FIRST_RESULT_DESCRIPTION, example = FIRST_RESULT_EXAMPLE)
                                           @RequestParam final int firstResult,
                                           @ApiParam(value = MAX_RESULTS_DESCRIPTION, example = MAX_RESULTS_EXAMPLE)
                                           @RequestParam final int maxResults) {
        return communityService.getCommunityPosts(communityId, firstResult, maxResults);
    }

    @GetMapping("/subscriptions")
    @ApiOperation(value = GET_SUBSCRIBED_COMMUNITIES_DESCRIPTION, response = CommunityDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_COMMUNITIES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<CommunityDto> getSubscribedCommunities(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                                 example = FIRST_RESULT_EXAMPLE)
                                                       @RequestParam final int firstResult,
                                                       @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                                 example = MAX_RESULTS_EXAMPLE)
                                                       @RequestParam final int maxResults,
                                                       HttpServletRequest request) {
        return communityService.getSubscribedCommunities(request, firstResult, maxResults, signingKey.getSecretKey());
    }

    @PutMapping("/{id}/subscriptions")
    @ApiOperation(value = SUBSCRIBE_TO_COMMUNITY_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = SUBSCRIBE_TO_COMMUNITY_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto subscribeToCommunity(@ApiParam(value = COMMUNITY_ID_DESCRIPTION,
                                                           example = COMMUNITY_ID_FOR_SUBSCRIBE_POSTS)
                                                 @PathVariable("id") final Long communityId,
                                                 final HttpServletRequest request) {
        communityService.subscribeToCommunity(request, communityId, signingKey.getSecretKey());
        return new ClientMessageDto(SUBSCRIBE_TO_COMMUNITY_OK_MESSAGE);
    }

    @PutMapping("/{id}/subscriptions/changes")
    @ApiOperation(value = UNSUBSCRIBE_FROM_COMMUNITY_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = UNSUBSCRIBE_TO_COMMUNITY_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto unsubscribeFromCommunity(@ApiParam(value = COMMUNITY_ID_DESCRIPTION,
                                                               example = COMMUNITY_ID_FOR_SUBSCRIBE_POSTS)
                                                     @PathVariable("id") final Long communityId,
                                                     final HttpServletRequest request) {
        communityService.unsubscribeFromCommunity(request, communityId, signingKey.getSecretKey());
        return new ClientMessageDto(UNSUBSCRIBE_TO_COMMUNITY_OK_MESSAGE);
    }

    @PostMapping
    @ApiOperation(value = ADD_COMMUNITY_DESCRIPTION, response = CommunityDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = ADD_COMMUNITY_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public CommunityDto addCommunity(@ApiParam(value = COMMUNITY_DTO_DESCRIPTION)
                                     @RequestBody @Valid final CommunityForCreateDto communityDto,
                                     final HttpServletRequest request) {
        return communityService.addCommunity(request, communityDto, signingKey.getSecretKey());
    }

    @PutMapping
    @ApiOperation(value = UPDATE_COMMUNITY_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = CREATED, message = UPDATE_COMMUNITY_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto updateCommunity(@ApiParam(value = COMMUNITY_DTO_DESCRIPTION)
                                            @RequestBody @Valid final CommunityDto communityDto,
                                            final HttpServletRequest request) {
        communityService.updateCommunity(request, communityDto, signingKey.getSecretKey());
        return new ClientMessageDto(UPDATE_COMMUNITY_OK_MESSAGE);
    }

    @PutMapping("/{id}/changes")
    @ApiOperation(value = DELETE_COMMUNITY_BY_USER_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_COMMUNITY_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteCommunityByUser(@ApiParam(value = COMMUNITY_ID_DESCRIPTION,
                                                            example = COMMUNITY_ID_FOR_DELETE)
                                                  @PathVariable("id") final Long communityId,
                                                  final HttpServletRequest request) {
        communityService.deleteCommunityByUser(request, communityId, signingKey.getSecretKey());
        return new ClientMessageDto(DELETE_COMMUNITY_OK_MESSAGE);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ApiOperation(value = DELETE_COMMUNITY_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_COMMUNITY_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteCommunity(@ApiParam(value = COMMUNITY_ID_DESCRIPTION,
                                                      example = COMMUNITY_ID_FOR_DELETE)
                                            @PathVariable("id") final Long communityId) {
        communityService.deleteCommunity(communityId);
        return new ClientMessageDto(DELETE_COMMUNITY_OK_MESSAGE);
    }

    @PostMapping("/{id}/posts")
    @ApiOperation(value = ADD_POST_TO_COMMUNITY_DESCRIPTION, response = PostDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = ADD_POST_TO_COMMUNITY_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto addPostToCommunity(@ApiParam(value = POST_DTO_DESCRIPTION)
                                      @RequestBody @Valid final PostForCreationDto postDto,
                                      @ApiParam(value = COMMUNITY_ID_DESCRIPTION,
                                                example = COMMUNITY_ID_FOR_ADD_POST)
                                      @PathVariable("id") final Long communityId,
                                      final HttpServletRequest request) {
        return communityService.addPostToCommunity(request, postDto, communityId, signingKey.getSecretKey());
    }

}
