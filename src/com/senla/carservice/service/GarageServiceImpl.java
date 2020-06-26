package com.senla.carservice.service;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.repository.GarageRepository;
import com.senla.carservice.repository.GarageRepositoryImpl;
import com.senla.carservice.util.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GarageServiceImpl implements GarageService {
    private static GarageService instance;
    private final GarageRepository garageRepository;
    // чтобы это стало константой, надо стелать поле статик
    private final String GARAGE_PATH = "csv//garage.csv";
    private final String PLACE_PATH = "csv//place.csv";

    private GarageServiceImpl() {
        this.garageRepository = GarageRepositoryImpl.getInstance();
    }

    public static GarageService getInstance() {
        if (instance == null) {
            instance = new GarageServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Garage> getGarages() {
        return this.garageRepository.getGarages();
    }

    @Override
    public void addGarage(String name) {
        Garage garage = new Garage(name);
        garage.setId(this.garageRepository.getIdGeneratorGarage().getId());
        // метод добавления должен быть не здесь, а в репозитории
        // туда же можно перенести генерацию айди, здесь она не нужна
        this.garageRepository.getGarages().add(garage);
    }

    @Override
    public void deleteGarage(Garage garage) {
        // метод удаления должен быть в репозитории
        // this в сервисе обычно не пишут
        this.garageRepository.getGarages().remove(garage);
    }

    @Override
    public void addGaragePlace(Garage garage) {
        Place place = new Place();
        place.setId(this.garageRepository.getIdGeneratorPlace().getId());
        // один из конструкторов Гаража не содержит инициализацию коллекции - будет НПЕ
        garage.getPlaces().add(place);
    }

    @Override
    public int getNumberPlaces() {
        int numberPlaces = 0;
        for (Garage garage : this.garageRepository.getGarages()) {
            numberPlaces += garage.getPlaces().size();
        }
        return numberPlaces;
    }

    @Override
    public void deleteGaragePlace(Garage garage) {
        // я не понимаю, что тут происходит - getPlaces вызывается 3!!! раза в одной строчке
        garage.getPlaces().remove(garage.getPlaces().get(garage.getPlaces().size() - 1));
    }

    @Override
    public List<Garage> getGaragesFreePlace(Date executeDate, Date leadDate, List<Order> orders) {
        if (executeDate == null || leadDate == null) {
            return new ArrayList<>();
        }

        // метод получения выборки только свободных гаражей может быть в репозитории
        List<Garage> freeGarages = new ArrayList<>(this.garageRepository.getGarages());
        for (Order order : orders) {
            for (Garage garage : freeGarages) {
                if (garage.equals(order.getGarage())) {
                    garage.getPlaces().remove(order.getPlace());
                    break;
                }
            }
        }
        return freeGarages;
    }

    @Override
    public List<Place> getFreePlaceGarage(Garage garage) {
        List<Place> freePlaces = new ArrayList<>();
        for (Place place : garage.getPlaces())
            if (!place.isBusyStatus()) {
                freePlaces.add(place);
            }
        return freePlaces;
    }

    // метод называется экпорт гаражей, но экспортирует он не только гаражи
    @Override
    public String exportGarages() {
        // у тебя есть такое поле в классе, зачем получать еще раз?
        GarageRepository garageRepository = GarageRepositoryImpl.getInstance();
        List<Garage> garages = garageRepository.getGarages();
        List<Place> places = garageRepository.getPlaces();
        StringBuilder valueGarageCsv = new StringBuilder();
        StringBuilder valuePlaceCsv = new StringBuilder();
        String message;
        for (int i = 0; i < garages.size(); i++) {
            // вот эти проверки можно не делать, если в чтение из файла добавить проверку на пустую линию
            // это обезопасит чтение от проблем пустых строк в середине файла
            // и уберет лишние аргументы и проверки при сохранении
            if (i == garages.size() - 1) {
                valueGarageCsv.append(convertGarageToCsv(garages.get(i), false));
            } else {
                valueGarageCsv.append(convertGarageToCsv(garages.get(i), true));
            }
        }
        for (int i = 0; i < places.size(); i++) {
            if (i == places.size() - 1) {
                valuePlaceCsv.append(convertPlaceToCsv(places.get(i), false));
            } else {
                valuePlaceCsv.append(convertPlaceToCsv(places.get(i), true));
            }
        }

        // тема исключения - ипользуй их, это лучше, чем сравнивать строки (это надежней)
        message = FileUtil.SaveCsv(valueGarageCsv, GARAGE_PATH);
        if (!message.equals("save successfully")) {
            return message;
        }
        message = FileUtil.SaveCsv(valuePlaceCsv, PLACE_PATH);
        if (!message.equals("save successfully")) {
            return message;
        }
        return message;
    }

    @Override
    public String importGarage() {
        List<String> csvLinesGarage = FileUtil.GetCsv(GARAGE_PATH);
        List<Place> places = importPlace();
        if (places.isEmpty() || csvLinesGarage.isEmpty()) {
            return "import problem";
        }
        List<Garage> garages = this.garageRepository.getGarages();
        csvLinesGarage.forEach(line -> {
            Garage garage = getGarageFromCsv(line, places);
            garages.remove(garage);
            garages.add(garage);
        });
        return "import successfully";
    }

    private List<Place> importPlace() {
        List<String> csvLinesPlace = FileUtil.GetCsv(PLACE_PATH);
        List<Place> places = new ArrayList<>();
        csvLinesPlace.forEach(line -> places.add(getPlaceFromCsv(line)));
        return places;
    }

    private Place getPlaceFromCsv(String line) {
        // разделители в константы
        List<String> values = Arrays.asList(line.split(","));
        Place place = new Place();
        place.setId(Long.valueOf(values.get(0)));
        place.setBusyStatus(Boolean.valueOf(values.get(1)));
        return place;
    }

    private Garage getGarageFromCsv(String line, List<Place> places) {
        String values = Arrays.asList(line.split("\"")).get(0);
        // placeId
        String idPlace = Arrays.asList(line.split("\"")).get(1);
        Garage garage = new Garage();
        // желательно предусмотреть падения при некорректном формате - тема задания Исключения
        garage.setId(Long.valueOf(Arrays.asList(values.split(",")).get(0)));
        garage.setName(Arrays.asList(values.split(",")).get(1));
        List<Place> garagePlace = new ArrayList<>();
        for (String stringIndex : idPlace.replaceAll("\"", "").split(",")) {
            places.forEach(place -> {
                if (place.getId().equals(Long.valueOf(stringIndex))) {
                    garagePlace.add(place);
                }
            });
        }
        garage.setPlaces(garagePlace);
        return garage;
    }

    private String convertGarageToCsv(Garage garage, boolean isLineFeed) {
        StringBuilder stringValue = new StringBuilder();
        // неэффективно использовать стринг формат и стринг билдер на такой строчке
        // разделители должны быть в константах
        stringValue.append(String.format("%s,%s,\"", garage.getId(), garage.getName()));
        for (int i = 0; i < garage.getPlaces().size(); i++) {
            if (i == garage.getPlaces().size() - 1) {
                stringValue.append(garage.getPlaces().get(i).getId());
            } else {
                stringValue.append(garage.getPlaces().get(i).getId()).append(",");
            }
        }
        stringValue.append("\"");
        if (isLineFeed) {
            stringValue.append("\n");
        }
        return stringValue.toString();
    }

    private String convertPlaceToCsv(Place place, boolean isLineFeed) {
        if (isLineFeed) {
            return String.format("%s,%s\n", place.getId(), place.isBusyStatus());
        } else {
            return String.format("%s,%s", place.getId(), place.isBusyStatus());
        }
    }
}