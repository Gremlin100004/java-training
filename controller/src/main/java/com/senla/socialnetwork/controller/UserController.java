package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.UserDto;
import com.senla.socialnetwork.service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
@NoArgsConstructor
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam int firstResult, @RequestParam int maxResults) {
        return userService.getUsers(firstResult, maxResults);
    }

    @GetMapping("/own")
    public UserDto getUser(@RequestParam int firstResult, @RequestParam int maxResults, Authentication authentication) {
        return userService.getUser(authentication.getName());
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
    public ClientMessageDto logOut(Authentication authentication, HttpServletRequest request) {
        userService.logOut(authentication.getName(), request);
        return new ClientMessageDto("Logout was successful");
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deleteUser(@PathVariable("id") Long orderId) {
        userService.deleteUser(orderId);
        return new ClientMessageDto("User has been deleted successfully");
    }

}

