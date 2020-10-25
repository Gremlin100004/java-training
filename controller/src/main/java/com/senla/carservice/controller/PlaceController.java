package com.senla.carservice.controller;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.LongDto;
import com.senla.carservice.dto.PlaceDto;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceDto addPlace(@RequestBody PlaceDto placeDto) {
        return placeService.addPlace(placeDto);
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

    @GetMapping("/numberPlaces")
    public LongDto getNumberFreePlaces(@RequestParam(required = false) String date) {
        LongDto longDto = new LongDto();
        if (date == null) {
            longDto.setNumber(placeService.getNumberPlace());
        } else {
            Date dateFree = DateUtil.getDatesFromString(date, false);
            Date startDayDate = DateUtil.bringStartOfDayDate(dateFree);
            longDto.setNumber(placeService.getNumberFreePlaceByDate(startDayDate));
        }
        return longDto;
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ClientMessageDto deletePlace(@PathVariable("id") Long orderId) {
        placeService.deletePlace(orderId);
        return new ClientMessageDto("Place has been deleted successfully");
    }
}
