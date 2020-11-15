package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.exception.ControllerException;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.service.CommunityService;
import com.senla.socialnetwork.service.enumaration.CommunitySortParameter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/communities")
@NoArgsConstructor
public class CommunityController {
    @Autowired
    private CommunityService communityService;

    @GetMapping("/all")
    public List<CommunityDto> getAllCommunities(@RequestParam int firstResult, @RequestParam int maxResults) {
        return communityService.getAllCommunities(firstResult, maxResults);
    }

    @GetMapping
    public List<CommunityDto> getCommunities(@RequestParam(required = false) CommunitySortParameter sortParameter,
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
    public List<CommunityDto> getOwnCommunities(@RequestParam int firstResult,
                                                @RequestParam int maxResults,
                                                Authentication authentication) {
        String email = authentication.getName();
        return communityService.getOwnCommunities(email, firstResult, maxResults);
    }

    @GetMapping("/subscriptions")
    public List<CommunityDto> getSubscribedCommunities(@RequestParam int firstResult,
                                                       @RequestParam int maxResults,
                                                       Authentication authentication) {
        String email = authentication.getName();
        return communityService.getSubscribedCommunities(email, firstResult, maxResults);
    }

    @GetMapping("/{id}/posts")
    public List<PostDto> getCommunityPosts(@PathVariable("id") Long communityId,
                                           @RequestParam int firstResult,
                                           @RequestParam int maxResults) {
        return communityService.getCommunityPosts(communityId, firstResult, maxResults);
    }

    @PutMapping("/{id}/subscriptions")
    public ClientMessageDto subscribeToCommunity(@PathVariable("id") Long communityId, Authentication authentication) {
        String email = authentication.getName();
        communityService.subscribeToCommunity(email, communityId);
        return new ClientMessageDto("Community subscription was successful");
    }

    @PutMapping("/{id}/subscriptions/changes")
    public ClientMessageDto unsubscribeFromCommunity(@PathVariable("id") Long communityId,
                                                     Authentication authentication) {
        String email = authentication.getName();
        communityService.unsubscribeFromCommunity(email, communityId);
        return new ClientMessageDto("Community unsubscription was successful");
    }

    @PostMapping
    public CommunityDto addCommunity(@RequestBody CommunityDto communityDto) {
        return communityService.addCommunity(communityDto);
    }

    @PutMapping
    public ClientMessageDto updateCommunity(@RequestBody CommunityDto communityDto) {
        communityService.updateCommunity(communityDto);
        return new ClientMessageDto("Community updated successfully");
    }

    @PutMapping("/{id}/changes")
    public ClientMessageDto deleteCommunityByUser(@PathVariable("id") Long communityId, Authentication authentication) {
        String email = authentication.getName();
        communityService.deleteCommunityByUser(email, communityId);
        return new ClientMessageDto("Community deleted successfully");
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deleteCommunity(@PathVariable("id") Long communityId) {
        communityService.deleteCommunity(communityId);
        return new ClientMessageDto("Community deleted successfully");
    }

    @PostMapping("/{id}/posts")
    public ClientMessageDto addPostToCommunity(@RequestBody PostDto postDto,
                                               @PathVariable("id") Long communityId,
                                               Authentication authentication) {
        String email = authentication.getName();
        communityService.addPostToCommunity(email, postDto, communityId);
        return new ClientMessageDto("Post added successfully");
    }

}
