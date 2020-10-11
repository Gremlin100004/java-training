package com.senla.carservice.controller;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.PlaceDto;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/places")
@NoArgsConstructor
@Slf4j
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @PostMapping
    public PlaceDto addPlace(@RequestBody PlaceDto placeDto) {
        return placeService.addPlace(placeDto);
    }

    @GetMapping("check")
    public ClientMessageDto checkPlaces() {
        placeService.checkPlaces();
        return new ClientMessageDto("verification was successfully");
    }

    @GetMapping
    public List<PlaceDto> getPlaces(@RequestParam(required = false) String stringExecuteDate) {
        if (stringExecuteDate == null) {
            return placeService.getPlaces();
        } else {
            Date executeDate = DateUtil.getDatesFromString(stringExecuteDate.replace('%', ' '), true);
            return placeService.getFreePlaceByDate(executeDate);
        }
    }

    @DeleteMapping("{id}")
    public ClientMessageDto deletePlace(@PathVariable("id") Long orderId) {
        placeService.deletePlace(orderId);
        return new ClientMessageDto("Delete place in service successfully");
    }
}
