package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.service.PublicMessageCommentService;
import io.swagger.annotations.Api;
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

import java.util.List;

@RestController
@RequestMapping("/publicMessages/comments")
@Api(tags = "Public Messages Comments")
@NoArgsConstructor
public class PublicMessageCommentController {
    @Autowired
    private PublicMessageCommentService publicMessageCommentService;

    @GetMapping
    public List<PublicMessageCommentDto> getComments(@RequestParam int firstResult, @RequestParam int maxResults) {
        return publicMessageCommentService.getComments(firstResult, maxResults);
    }

    @PostMapping
    public PublicMessageCommentDto addComment(@RequestBody PublicMessageCommentDto publicMessageCommentDto) {
        return publicMessageCommentService.addComment(publicMessageCommentDto);
    }

    @PutMapping
    public ClientMessageDto updateComment(@RequestBody PublicMessageCommentDto publicMessageCommentDto) {
        publicMessageCommentService.updateComment(publicMessageCommentDto);
        return new ClientMessageDto("Comment updated successfully");
    }

    @PutMapping("/{id}/changes")
    public ClientMessageDto deleteCommentByUser(@PathVariable("id") Long commentId) {
        String email = "";
//        String email = authentication.getName();
        publicMessageCommentService.deleteCommentByUser(email, commentId);
        return new ClientMessageDto("Comment deleted successfully");
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deleteComment(@PathVariable("id") Long commentId) {
        publicMessageCommentService.deleteComment(commentId);
        return new ClientMessageDto("Comment deleted successfully");
    }

}
