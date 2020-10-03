package com.senla.carservice.ui;

import com.senla.carservice.dto.MasterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Requests {

    public static void get(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/controller/masters";
        ResponseEntity<MasterDto[]> response =
            restTemplate.getForEntity(url, MasterDto[].class);
        MasterDto[] mastersDto = response.getBody();
        for (MasterDto masterDto:mastersDto) {
            System.out.println(masterDto);
        }
    }

    public static void test(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/controller/masters";
        ResponseEntity<MasterDto> response = restTemplate.getForEntity(url, MasterDto.class);
    }

//    public static void main(String[] args) {
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Requests.class);
//        CarOfficeRequest carOfficeRequest = applicationContext.getBean(CarOfficeRequest.class);
//        String date = "10.15.2020";
//        System.out.println(carOfficeRequest.getFreePlacesMastersByDate(date));
//    }
}
