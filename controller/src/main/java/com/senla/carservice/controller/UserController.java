package com.senla.carservice.controller;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.UserDto;
import com.senla.carservice.service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@NoArgsConstructor
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers() {
        return userService.getSystemUsers();
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PostMapping("/authorization")
    @ResponseStatus(HttpStatus.OK)
    public ClientMessageDto logIn(@RequestBody UserDto userDto) {
        return new ClientMessageDto(userService.logIn(userDto));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientMessageDto deletePlace(@PathVariable("id") Long orderId) {
        userService.deleteUser(orderId);
        return new ClientMessageDto("User has been deleted successfully");
    }

}
