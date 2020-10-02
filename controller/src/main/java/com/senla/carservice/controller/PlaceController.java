package com.senla.carservice.controller;

import com.senla.carservice.dto.PlaceDto;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
@NoArgsConstructor
@Slf4j
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @PostMapping("places")
    public PlaceDto addPlace(@RequestBody PlaceDto placeDto) {
        log.info("Method addPlace");
        log.trace("Parameter placeDto: {}", placeDto);
        return placeService.addPlace(placeDto);
    }

    @GetMapping("places/check")
    public String checkPlaces() {
        log.info("Method checkPlaces");
        if (placeService.getNumberPlace() == 0) {
            throw new  BusinessException("There are no places");
        }
        return "verification was successfully";
    }

    @GetMapping("places")
    public List<PlaceDto> getPlaces() {
        log.info("Method getArrayPlace");
        return placeService.getPlaces();
    }

    //ToDo in ui get with id

    @DeleteMapping("places/{id}")
    public String deletePlace(@PathVariable("id") Long orderId) {
        log.info("Method deletePlace");
        log.trace("Parameter orderId: {}", orderId);
        placeService.deletePlace(orderId);
        return " -delete place in service successfully";
    }

    @GetMapping("places/free-by-date")
    public List<PlaceDto> getFreePlacesByDate(@RequestParam String stringExecuteDate) {
        log.info("Method getFreePlacesByDate");
        log.trace("Parameter stringExecuteDate: {}", stringExecuteDate);
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
        return placeService.getFreePlaceByDate(executeDate);
    }
}