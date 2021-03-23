package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.util.ValidationUtil;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.dto.UniversityForCreateDto;
import com.senla.socialnetwork.service.UniversityService;
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
@RequestMapping("/universities")
@Api(tags = "Universities")
@NoArgsConstructor
public class UniversityController {
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    public static final String RETURN_LIST_OF_UNIVERSITIES_OK_MESSAGE = "Successfully retrieved list of university";
    public static final String RETURN_UNIVERSITY_OK_MESSAGE = "Successfully retrieved a university";
    public static final String UPDATE_UNIVERSITY_OK_MESSAGE = "Successfully updated a university";
    public static final String DELETE_UNIVERSITY_OK_MESSAGE = "Successfully deleted a university";
    public static final String FIRST_RESULT_DESCRIPTION = "The number of the first element of the expected list";
    public static final String MAX_RESULTS_DESCRIPTION = "Maximum number of list elements";
    public static final String FIRST_RESULT_EXAMPLE = "1";
    public static final String MAX_RESULTS_EXAMPLE = "10";
    public static final String LOCATION_DTO_DESCRIPTION = "DTO location";
    public static final String LOCATION_ID_DESCRIPTION = "Location id";
    public static final String GET_UNIVERSITIES_DESCRIPTION = "This method is used to get universities";
    public static final String ADD_UNIVERSITY_DESCRIPTION = "This method is used to add new university by admin";
    public static final String UPDATE_UNIVERSITY_DESCRIPTION = "This method is used to update university by admin";
    public static final String DELETE_UNIVERSITY_DESCRIPTION = "This method is used to delete university by admin";
    @Autowired
    private UniversityService universityService;

    @GetMapping
    @ApiOperation(value = GET_UNIVERSITIES_DESCRIPTION, response = UniversityDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_UNIVERSITIES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<UniversityDto> getUniversities(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                         example = FIRST_RESULT_EXAMPLE)
                                               @RequestParam final int firstResult,
                                               @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                         example = MAX_RESULTS_EXAMPLE)
                                               @RequestParam final int maxResults) {
        return universityService.getUniversities(firstResult, maxResults);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    @ApiOperation(value = ADD_UNIVERSITY_DESCRIPTION, response = UniversityDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = CREATED, message = RETURN_UNIVERSITY_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public UniversityDto addUniversity(@ApiParam(value = LOCATION_DTO_DESCRIPTION)
                                       @RequestBody final UniversityForCreateDto universityDto) {
        ValidationUtil.validate(universityDto);
        return universityService.addUniversity(universityDto);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping
    @ApiOperation(value = UPDATE_UNIVERSITY_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = UPDATE_UNIVERSITY_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto updateUniversity(@ApiParam(value = LOCATION_DTO_DESCRIPTION)
                                             @RequestBody final UniversityDto universityDto) {
        ValidationUtil.validate(universityDto);
        universityService.updateUniversity(universityDto);
        return new ClientMessageDto(UPDATE_UNIVERSITY_OK_MESSAGE);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ApiOperation(value = DELETE_UNIVERSITY_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_UNIVERSITY_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteUniversity(@ApiParam(value = LOCATION_ID_DESCRIPTION)
                                             @PathVariable("id") final Long universityId) {
        universityService.deleteUniversity(universityId);
        return new ClientMessageDto(DELETE_UNIVERSITY_OK_MESSAGE);
    }

}
