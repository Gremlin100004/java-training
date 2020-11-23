package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.UserDto;
import com.senla.socialnetwork.service.UserService;
import io.swagger.annotations.Api;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.List;

@RestController
@RequestMapping("/users")
@Api(tags = "Users")
@NoArgsConstructor
@Slf4j
public class UserController {
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String BAD_REQUEST_MESSAGE = "Successfully retrieved list";
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam int firstResult, @RequestParam int maxResults) {
        return userService.getUsers(firstResult, maxResults);
    }

    @GetMapping("/own")
    public UserDto getUser(@RequestParam int firstResult, @RequestParam int maxResults, HttpServletRequest request) {
        return userService.getUser(request);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PutMapping("/login")
    public ClientMessageDto logIn(@RequestBody UserDto userDto) {
        return new ClientMessageDto(userService.logIn(userDto));
    }

    @PutMapping("/logout")
    public ClientMessageDto logOut(HttpServletRequest request) {
        userService.logOut(request);
        return new ClientMessageDto("Logout was successful");
    }

    @PutMapping
    public ClientMessageDto updateUser(HttpServletRequest request, @RequestBody List<UserDto> usersDto) {
        userService.updateUser(request, usersDto);
        return new ClientMessageDto("Data update was successful");
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deleteUser(@PathVariable("id") Long orderId) {
        userService.deleteUser(orderId);
        return new ClientMessageDto("User has been deleted successfully");
    }

}

