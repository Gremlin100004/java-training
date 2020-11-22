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
