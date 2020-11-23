package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.service.PostService;
import io.swagger.annotations.Api;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/posts")
@Api(tags = "Posts")
@NoArgsConstructor
public class PostController {
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String BAD_REQUEST_MESSAGE = "Successfully retrieved list";
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    @Autowired
    PostService postService;

    @GetMapping
    public List<PostDto> getPosts(@RequestParam int firstResult, @RequestParam int maxResults) {
        return postService.getPosts(firstResult, maxResults);
    }

    @GetMapping("/history")
    public List<PostDto> getPostsHistory(@RequestParam int firstResult,
                                         @RequestParam int maxResults,
                                         HttpServletRequest request) {
        return postService.getPostsFromSubscribedCommunities(request, firstResult, maxResults);
    }

    @PutMapping
    public ClientMessageDto updatePost(@RequestBody PostDto postDto) {
        postService.updatePost(postDto);
        return new ClientMessageDto("Post updated successfully");
    }

    @PutMapping("/{id}/changes")
    public ClientMessageDto deletePostByUser(@PathVariable("id") Long postId, HttpServletRequest request) {
        postService.deletePostByUser(request, postId);
        return new ClientMessageDto("Post deleted successfully");
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deletePost(@PathVariable("id") Long postId) {
        postService.deletePost(postId);
        return new ClientMessageDto("Post deleted successfully");
    }

    @GetMapping("/{id}/comments")
    public List<PostCommentDto> getPostComments(@PathVariable("id") Long postId,
                                                @RequestParam int firstResult,
                                                @RequestParam int maxResults) {
        return postService.getPostComments(postId, firstResult, maxResults);
    }

}
