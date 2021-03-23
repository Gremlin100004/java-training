package com.senla.socialnetwork.dao.testdata;

import com.senla.socialnetwork.model.Location;

import java.util.ArrayList;
import java.util.List;

public final class LocationTestData {
    private static final String COUNTRY_STRING = "Belarus";
    private static final String CITY_MINSK_STRING = "Minsk";
    private static final String CITY_GOMEL_STRING = "Gomel";
    private static final String CITY_VITEBSK_STRING = "Vitebsk";
    private static final String CITY_GRODNO_STRING = "Grodno";
    private static final String CITY_BREST_STRING = "Brest";
    private static final String CITY_BOBRUISK_STRING = "Bobruisk";
    private static final String CITY_BARANOVICHI_STRING = "Baranovichi";
    private LocationTestData() {
    }

    private static Location getLocation(String country, String city){
        Location location = new Location();
        location.setCountry(country);
        location.setCity(city);
        location.setSchools(new ArrayList<>());
        location.setUniversities(new ArrayList<>());
        location.setUserProfiles(new ArrayList<>());
        location.setWeatherConditions(new ArrayList<>());
        return location;
    }

    public static List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();
        locations.add(getLocation(COUNTRY_STRING, CITY_MINSK_STRING));
        locations.add(getLocation(COUNTRY_STRING, CITY_GOMEL_STRING));
        locations.add(getLocation(COUNTRY_STRING, CITY_VITEBSK_STRING));
        locations.add(getLocation(COUNTRY_STRING, CITY_GRODNO_STRING));
        locations.add(getLocation(COUNTRY_STRING, CITY_BREST_STRING));
        locations.add(getLocation(COUNTRY_STRING, CITY_BOBRUISK_STRING));
        locations.add(getLocation(COUNTRY_STRING, CITY_BARANOVICHI_STRING));
        return locations;
    }

}
