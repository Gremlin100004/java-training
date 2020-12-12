package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.util.ValidationUtil;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.SchoolForCreateDto;
import com.senla.socialnetwork.service.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NoArgsConstructor;
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
@RequestMapping("/schools")
@Api(tags = "Schools")
@NoArgsConstructor
public class SchoolController {
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    public static final String FIRST_RESULT_DESCRIPTION = "The number of the first element of the expected list";
    public static final String MAX_RESULTS_DESCRIPTION = "Maximum number of list elements";
    public static final String FIRST_RESULT_EXAMPLE = "1";
    public static final String MAX_RESULTS_EXAMPLE = "10";
    public static final String RETURN_LIST_OF_SCHOOLS_OK_MESSAGE = "Successfully retrieved list of schools";
    public static final String RETURN_SCHOOL_OK_MESSAGE = "Successfully retrieved a school";
    public static final String UPDATE_SCHOOL_OK_MESSAGE = "Successfully updated a school";
    public static final String DELETE_SCHOOL_OK_MESSAGE = "Successfully deleted a school";
    public static final String SCHOOL_DTO_DESCRIPTION = "DTO school";
    public static final String SCHOOL_ID_DESCRIPTION = "School id";
    public static final String GET_SCHOOLS_DESCRIPTION = "This method is used to get schools";
    public static final String ADD_SCHOOL_DESCRIPTION = "This method is used to add new school by admin";
    public static final String UPDATE_SCHOOL_DESCRIPTION = "This method is used to update school by admin";
    public static final String DELETE_SCHOOL_DESCRIPTION = "This method is used to delete school by admin";
    @Autowired
    private SchoolService schoolService;

    @GetMapping
    @ApiOperation(value = GET_SCHOOLS_DESCRIPTION, response = SchoolDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_SCHOOLS_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<SchoolDto> getSchools(@ApiParam(value = FIRST_RESULT_DESCRIPTION, example = FIRST_RESULT_EXAMPLE)
                                      @RequestParam final int firstResult,
                                      @ApiParam(value = MAX_RESULTS_DESCRIPTION, example = MAX_RESULTS_EXAMPLE)
                                      @RequestParam final int maxResults) {
        return schoolService.getSchools(firstResult, maxResults);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    @ApiOperation(value = ADD_SCHOOL_DESCRIPTION, response = SchoolDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = CREATED, message = RETURN_SCHOOL_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolDto addSchool(@ApiParam(value = SCHOOL_DTO_DESCRIPTION)
                               @RequestBody final SchoolForCreateDto schoolDto) {
        ValidationUtil.validate(schoolDto);
        return schoolService.addSchool(schoolDto);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping
    @ApiOperation(value = UPDATE_SCHOOL_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = UPDATE_SCHOOL_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto updateSchool(@ApiParam(value = SCHOOL_DTO_DESCRIPTION)
                                         @RequestBody final SchoolDto schoolDto) {
        ValidationUtil.validate(schoolDto);
        schoolService.updateSchool(schoolDto);
        return new ClientMessageDto(UPDATE_SCHOOL_OK_MESSAGE);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ApiOperation(value = DELETE_SCHOOL_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_SCHOOL_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteSchool(@ApiParam(value = SCHOOL_ID_DESCRIPTION)
                                         @PathVariable("id") final Long schoolId) {
        schoolService.deleteSchool(schoolId);
        return new ClientMessageDto(DELETE_SCHOOL_OK_MESSAGE);
    }

}
