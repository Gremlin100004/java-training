package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.service.PostService;
import io.swagger.annotations.Api;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
@Api(tags = "Posts")
@NoArgsConstructor
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping
    public List<PostDto> getPosts(@RequestParam int firstResult, @RequestParam int maxResults) {
        return postService.getPosts(firstResult, maxResults);
    }

    @GetMapping("/history")
    public List<PostDto> getPostsHistory(@RequestParam int firstResult,
                                         @RequestParam int maxResults,
                                         Authentication authentication) {
        String email = authentication.getName();
        return postService.getPostsFromSubscribedCommunities(email, firstResult, maxResults);
    }

    @PutMapping
    public ClientMessageDto updatePost(@RequestBody PostDto postDto) {
        postService.updatePost(postDto);
        return new ClientMessageDto("Post updated successfully");
    }

    @PutMapping("/{id}/changes")
    public ClientMessageDto deletePostByUser(@PathVariable("id") Long postId, Authentication authentication) {
        String email = authentication.getName();
        postService.deletePostByUser(email, postId);
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
