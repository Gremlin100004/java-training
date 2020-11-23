package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.service.UniversityService;
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
@RequestMapping("/universities")
@Api(tags = "Universities")
@NoArgsConstructor
public class UniversityController {
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String BAD_REQUEST_MESSAGE = "Successfully retrieved list";
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    @Autowired
    private UniversityService universityService;

    @GetMapping
    public List<UniversityDto> getUniversities(@RequestParam int firstResult, @RequestParam int maxResults) {
        return universityService.getUniversities(firstResult, maxResults);
    }

    @PostMapping
    public UniversityDto addUniversity(@RequestBody UniversityDto universityDto) {
        return universityService.addUniversity(universityDto);
    }

    @PutMapping
    public ClientMessageDto updateUniversity(@RequestBody UniversityDto universityDto) {
        universityService.updateUniversity(universityDto);
        return new ClientMessageDto("University updated successfully");
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deleteUniversity(@PathVariable("id") Long universityId) {
        universityService.deleteUniversity(universityId);
        return new ClientMessageDto("University deleted successfully");
    }

}
