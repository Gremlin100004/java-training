package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.service.LocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/locations")
@Api(tags = "Locations")
@NoArgsConstructor
public class LocationController {
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String BAD_REQUEST_MESSAGE = "Successfully retrieved list";
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    @Autowired
    LocationService locationService;

    @GetMapping
    @ApiOperation(value = "XXXX", response = List.class)
    public List<LocationDto> getLocations(@RequestParam int firstResult, @RequestParam int maxResults) {
        return locationService.getLocations(firstResult, maxResults);
    }

    @PostMapping
    @ApiOperation(value = "XXXX")
    public LocationDto addLocation(@RequestBody LocationDto locationDto) {
        return locationService.addLocation(locationDto);
    }

    @PutMapping
    @ApiOperation(value = "XXXX")
    public ClientMessageDto updateLocation(@RequestBody LocationDto locationDto) {
        locationService.updateLocation(locationDto);
        return new ClientMessageDto("Location updated successfully");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "XXXX")
    public ClientMessageDto deleteLocation(@PathVariable("id") Long locationId) {
        locationService.deleteLocation(locationId);
        return new ClientMessageDto("Location deleted successfully");
    }

}
