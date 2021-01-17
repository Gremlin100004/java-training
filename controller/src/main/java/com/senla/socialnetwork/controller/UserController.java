package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.SigningKey;
import com.senla.socialnetwork.controller.util.ValidationUtil;
import com.senla.socialnetwork.domain.enumaration.RoleName;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.UserForAdminDto;
import com.senla.socialnetwork.dto.UserForSecurityDto;
import com.senla.socialnetwork.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
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

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(tags = "Users")
@NoArgsConstructor
@Slf4j
public class UserController {
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    public static final String RETURN_LIST_OF_USERS_OK_MESSAGE = "Successfully retrieved list of users";
    public static final String RETURN_USER_OK_MESSAGE = "Successfully retrieved a user";
    public static final String ADD_USER_OK_MESSAGE = "Successfully registered a user";
    public static final String UPDATE_USER_OK_MESSAGE = "Successfully updated a user";
    public static final String LOGIN_OK_MESSAGE = "Logged in successfully";
    public static final String DELETE_USER_OK_MESSAGE = "Successfully deleted a user";
    public static final String FIRST_RESULT_DESCRIPTION = "The number of the first element of the expected list";
    public static final String MAX_RESULTS_DESCRIPTION = "Maximum number of list elements";
    public static final String FIRST_RESULT_EXAMPLE = "1";
    public static final String MAX_RESULTS_EXAMPLE = "10";
    public static final String USER_DTO_DESCRIPTION = "DTO user";
    public static final String USERS_DTO_DESCRIPTION = "List of two DTO users. First object include old login data, "
       + "the second object include new login data";
    public static final String USER_ID_DESCRIPTION = "User id";
    public static final String USER_ID_EXAMPLE = "6";
    public static final String GET_USERS_DESCRIPTION = "This method is used to get users by admin";
    public static final String GET_USER_DESCRIPTION = "This method is used to enable the user to get their"
       + " security data.";
    public static final String ADD_USER_DESCRIPTION = "This method is used to add new user";
    public static final String ADD_ADMIN_DESCRIPTION = "This method is used to add new admin";
    public static final String LOGIN_DESCRIPTION = "This method is used to authorize the user";
    public static final String UPDATE_USER_DESCRIPTION = "This method is used to update security data by this user";
    public static final String DELETE_USER_DESCRIPTION = "This method is used to delete user by admin";
    @Autowired
    private UserService userService;
    @Autowired
    private SigningKey signingKey;

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    @ApiOperation(value = GET_USERS_DESCRIPTION, response = UserForAdminDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_USERS_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<UserForAdminDto> getUsers(@ApiParam(value = FIRST_RESULT_DESCRIPTION, example = FIRST_RESULT_EXAMPLE)
                                          @RequestParam final int firstResult,
                                          @ApiParam(value = MAX_RESULTS_DESCRIPTION, example = MAX_RESULTS_EXAMPLE)
                                          @RequestParam final int maxResults) {
        return userService.getUsers(firstResult, maxResults);
    }

    @GetMapping("/own")
    @ApiOperation(value = GET_USER_DESCRIPTION, response = UserForSecurityDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_USER_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public UserForSecurityDto getUser() {
        return userService.getUser();
    }

    @PostMapping("/registration")
    @ApiOperation(value = ADD_USER_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = CREATED, message = ADD_USER_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ClientMessageDto addUser(@RequestBody final UserForSecurityDto userDto) {
        ValidationUtil.validate(userDto);
        userService.addUser(userDto, RoleName.ROLE_USER);
        return new ClientMessageDto(ADD_USER_OK_MESSAGE);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/registration/admin")
    @ApiOperation(value = ADD_ADMIN_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = CREATED, message = ADD_USER_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ClientMessageDto addAdmin(@RequestBody final UserForSecurityDto userDto) {
        ValidationUtil.validate(userDto);
        userService.addUser(userDto, RoleName.ROLE_ADMIN);
        return new ClientMessageDto(ADD_USER_OK_MESSAGE);
    }

    @PutMapping("/login")
    @ApiOperation(value = LOGIN_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = LOGIN_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto logIn(@ApiParam(value = USER_DTO_DESCRIPTION)
                                  @RequestBody final UserForSecurityDto userDto) {
        ValidationUtil.validate(userDto);
        return new ClientMessageDto(userService.logIn(userDto, signingKey.getSecretKey()));
    }

    @PutMapping
    @ApiOperation(value = UPDATE_USER_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = UPDATE_USER_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto updateUser(@ApiParam(value = USERS_DTO_DESCRIPTION)
                                       @RequestBody final List<UserForSecurityDto> usersDto) {
        ValidationUtil.validate(usersDto);
        userService.updateUser(usersDto);
        return new ClientMessageDto(UPDATE_USER_OK_MESSAGE);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ApiOperation(value = DELETE_USER_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_USER_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteUser(@ApiParam(value = USER_ID_DESCRIPTION,
                                                 example = USER_ID_EXAMPLE)
                                       @PathVariable("id") final Long orderId) {
        userService.deleteUser(orderId);
        return new ClientMessageDto(DELETE_USER_OK_MESSAGE);
    }

}
