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
        log.info("Method addPlace");
        log.trace("Parameter placeDto: {}", placeDto);
        return placeService.addPlace(placeDto);
    }

    @GetMapping("check")
    public ClientMessageDto checkPlaces() {
        log.info("Method checkPlaces");
        placeService.checkPlaces();
        return new ClientMessageDto("verification was successfully");
    }

    @GetMapping
    public List<PlaceDto> getPlaces() {
        log.info("Method getArrayPlace");
        return placeService.getPlaces();
    }

    @DeleteMapping("{id}")
    public ClientMessageDto deletePlace(@PathVariable("id") Long orderId) {
        log.info("Method deletePlace");
        log.trace("Parameter orderId: {}", orderId);
        placeService.deletePlace(orderId);
        return new ClientMessageDto(" -delete place in service successfully");
    }

    @GetMapping("freeByDate")
    public List<PlaceDto> getFreePlacesByDate(@RequestParam String stringExecuteDate) {
        log.info("Method getFreePlacesByDate");
        log.trace("Parameter stringExecuteDate: {}", stringExecuteDate);
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate.replace('%', ' '), true);
        return placeService.getFreePlaceByDate(executeDate);
    }

}
