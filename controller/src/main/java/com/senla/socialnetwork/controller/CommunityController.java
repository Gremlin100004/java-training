package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.exception.ControllerException;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.service.CommunityService;
import com.senla.socialnetwork.service.enumaration.CommunitySortParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import java.util.List;

@RestController
@RequestMapping("/communities")
@Api(tags = "Communities")
@NoArgsConstructor
public class CommunityController {
    public static final int OK = 200;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String OK_MESSAGE = "Successfully retrieved list";
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    @Autowired
    private CommunityService communityService;

    @GetMapping("/all")
    @ApiOperation(value = "This method is used to get absolutely all communities.", response = CommunityDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<CommunityDto> getAllCommunities(@ApiParam(value = "The number of the first element of the expected list")
                                                @RequestParam int firstResult,
                                                @ApiParam(value = "Maximum number of list elements")
                                                @RequestParam int maxResults) {
        return communityService.getAllCommunities(firstResult, maxResults);
    }

    @GetMapping
    @ApiOperation(value = "XXXX", response = List.class)
    public List<CommunityDto> getCommunities(
        @RequestParam(required = false) CommunitySortParameter sortParameter,
        @RequestParam(required = false) CommunityType communityType,
        @RequestParam int firstResult,
        @RequestParam int maxResults) {
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

    @GetMapping("/ownCommunities")
    @ApiOperation(value = "XXXX", response = List.class)
    public List<CommunityDto> getOwnCommunities(
        @RequestParam int firstResult, @RequestParam int maxResults, HttpServletRequest request) {
        return communityService.getOwnCommunities(request, firstResult, maxResults);
    }

    @GetMapping("/subscriptions")
    @ApiOperation(value = "XXXX", response = List.class)
    public List<CommunityDto> getSubscribedCommunities(
        @RequestParam int firstResult, @RequestParam int maxResults, HttpServletRequest request) {
        return communityService.getSubscribedCommunities(request, firstResult, maxResults);
    }

    @GetMapping("/{id}/posts")
    @ApiOperation(value = "XXXX", response = List.class)
    public List<PostDto> getCommunityPosts(
        @PathVariable("id") Long communityId, @RequestParam int firstResult, @RequestParam int maxResults) {
        return communityService.getCommunityPosts(communityId, firstResult, maxResults);
    }

    @PutMapping("/{id}/subscriptions")
    @ApiOperation(value = "XXXX", response = List.class)
    public ClientMessageDto subscribeToCommunity(@PathVariable("id") Long communityId,
                                                 HttpServletRequest request) {
        communityService.subscribeToCommunity(request, communityId);
        return new ClientMessageDto("Community subscription was successful");
    }

    @PutMapping("/{id}/subscriptions/changes")
    @ApiOperation(value = "XXXX", response = List.class)
    public ClientMessageDto unsubscribeFromCommunity(
        @PathVariable("id") Long communityId, HttpServletRequest request) {
        communityService.unsubscribeFromCommunity(request, communityId);
        return new ClientMessageDto("Community unsubscription was successful");
    }

    @PostMapping
    @ApiOperation(value = "XXXX", response = List.class)
    public CommunityDto addCommunity(@RequestBody CommunityDto communityDto) {
        return communityService.addCommunity(communityDto);
    }

    @PutMapping
    @ApiOperation(value = "XXXX", response = List.class)
    public ClientMessageDto updateCommunity(@RequestBody CommunityDto communityDto) {
        communityService.updateCommunity(communityDto);
        return new ClientMessageDto("Community updated successfully");
    }

    @PutMapping("/{id}/changes")
    @ApiOperation(value = "XXXX", response = List.class)
    public ClientMessageDto deleteCommunityByUser(@PathVariable("id") Long communityId, HttpServletRequest request) {
        communityService.deleteCommunityByUser(request, communityId);
        return new ClientMessageDto("Community deleted successfully");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "XXXX", response = List.class)
    public ClientMessageDto deleteCommunity(@PathVariable("id") Long communityId) {
        communityService.deleteCommunity(communityId);
        return new ClientMessageDto("Community deleted successfully");
    }

    @PostMapping("/{id}/posts")
    @ApiOperation(value = "XXXX", response = List.class)
    public ClientMessageDto addPostToCommunity(
        @RequestBody PostDto postDto, @PathVariable("id") Long communityId, HttpServletRequest request) {
        communityService.addPostToCommunity(request, postDto, communityId);
        return new ClientMessageDto("Post added successfully");
    }

}
