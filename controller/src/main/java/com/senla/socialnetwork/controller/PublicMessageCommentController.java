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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/publicMessages/comments")
@Api(tags = "Public Messages Comments")
@NoArgsConstructor
public class PublicMessageCommentController {
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String BAD_REQUEST_MESSAGE = "Successfully retrieved list";
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
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
    public ClientMessageDto deleteCommentByUser(@PathVariable("id") Long commentId, HttpServletRequest request) {
         publicMessageCommentService.deleteCommentByUser(request, commentId);
        return new ClientMessageDto("Comment deleted successfully");
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deleteComment(@PathVariable("id") Long commentId) {
        publicMessageCommentService.deleteComment(commentId);
        return new ClientMessageDto("Comment deleted successfully");
    }

}
