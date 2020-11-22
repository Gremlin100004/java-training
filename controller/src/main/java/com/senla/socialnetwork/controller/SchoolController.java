package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.service.SchoolService;
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
@RequestMapping("/schools")
@Api(tags = "Schools")
@NoArgsConstructor
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @GetMapping
    public List<SchoolDto> getSchools(@RequestParam int firstResult, @RequestParam int maxResults) {
        return schoolService.getSchools(firstResult, maxResults);
    }

    @PostMapping
    public SchoolDto addSchool(@RequestBody SchoolDto schoolDto) {
        return schoolService.addSchool(schoolDto);
    }

    @PutMapping
    public ClientMessageDto updateSchool(@RequestBody SchoolDto schoolDto) {
        schoolService.updateSchool(schoolDto);
        return new ClientMessageDto("School updated successfully");
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deleteSchool(@PathVariable("id") Long schoolId) {
        schoolService.deleteSchool(schoolId);
        return new ClientMessageDto("School deleted successfully");
    }

}
