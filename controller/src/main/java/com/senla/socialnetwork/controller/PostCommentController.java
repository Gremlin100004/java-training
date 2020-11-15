package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.service.PostCommentService;
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
@RequestMapping("/posts/comments")
@NoArgsConstructor
public class PostCommentController {
    @Autowired
    private PostCommentService postCommentService;

    @GetMapping
    public List<PostCommentDto> getComments(@RequestParam int firstResult, @RequestParam int maxResults) {
        return postCommentService.getComments(firstResult, maxResults);
    }

    @PostMapping
    public PostCommentDto addComment(@RequestBody PostCommentDto postCommentDto) {
        return postCommentService.addComment(postCommentDto);
    }

    @PutMapping
    public ClientMessageDto updateComment(@RequestBody PostCommentDto postCommentDto) {
        postCommentService.updateComment(postCommentDto);
        return new ClientMessageDto("Comment updated successfully");
    }

    @PutMapping("/{id}/changes")
    public ClientMessageDto deleteCommentByUser(@PathVariable("id") Long commentId, Authentication authentication) {
        String email = authentication.getName();
        postCommentService.deleteCommentByUser(email, commentId);
        return new ClientMessageDto("Comment deleted successfully");
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deleteComment(@PathVariable("id") Long commentId) {
        postCommentService.deleteComment(commentId);
        return new ClientMessageDto("Comment deleted successfully");
    }

}
