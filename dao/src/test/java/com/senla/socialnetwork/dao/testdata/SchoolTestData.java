package com.senla.socialnetwork.dao.testdata;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.Location;
import com.senla.socialnetwork.model.School;

import java.util.ArrayList;
import java.util.List;

public class SchoolTestData {
    private static final String[] NAME_ARRAY = {
            "Secondary school number 104",
            "Secondary school number 95",
            "Secondary school number 21",
            "Secondary school number 69",
            "Secondary school number 2",
            "Secondary school number 4",
            "Secondary school number 38",
            "Secondary school number 31",
            "Secondary school number 32",
            "Secondary school number 8",
            "Secondary school number 9",
            "Secondary school number 1",
            "Secondary school number 3",
            "Secondary school number 12",
            "Secondary school number 22",
    };

    public static List<School> getSchools(List<Location> locations) {
        List<School> schools = new ArrayList<>();
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.FIRST_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index)));
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.SECOND_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index)));
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.THIRD_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index)));
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.FOURTH_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index)));
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.FIFTH_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index)));
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.SIXTH_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index)));
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index)));
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index)));
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.NINTH_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index)));
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.TENTH_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index)));
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.ELEVENTH_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index)));
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.TWELFTH_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index)));
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.THIRTEENTH_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.SIXTH_INDEX_OF_ARRAY.index)));
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.FOURTEENTH_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index)));
        schools.add(getSchool(NAME_ARRAY[ArrayIndex.FIFTEENTH_INDEX_OF_ARRAY.index], locations.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index)));
        return schools;
    }

    private static School getSchool(String name, Location location) {
        School school = new School();
        school.setName(name);
        school.setLocation(location);
        school.setUserProfiles(new ArrayList<>());
        return school;
    }

}
