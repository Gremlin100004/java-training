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
