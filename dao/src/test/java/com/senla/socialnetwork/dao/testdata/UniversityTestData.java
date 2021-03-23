package com.senla.socialnetwork.dao.testdata;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.Location;
import com.senla.socialnetwork.model.University;

import java.util.ArrayList;
import java.util.List;

public final class UniversityTestData {
    private static final String[] NAME_ARRAY = {
            "Academy of Management under the President of the Republic of Belarus",
            "Belarusian State University",
            "Belarusian State University of Transport",
            "Gomel State Medical University",
            "Vitebsk State University named after P.M. Masherova",
            "Vitebsk State Technological University",
            "Grodno State Agrarian University",
            "Grodno State Medical University",
            "Baranovichi State University",
            "Brest State Technical University",
    };

    private UniversityTestData() {
    }

    public static List<University> getUniversities(List<Location> locations) {
        List<University> universities = new ArrayList<>();
        universities.add(getUniversity(NAME_ARRAY[ArrayIndex.FIRST_INDEX_OF_ARRAY.index], locations.get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index)));
        universities.add(getUniversity(NAME_ARRAY[ArrayIndex.SECOND_INDEX_OF_ARRAY.index], locations.get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index)));
        universities.add(getUniversity(NAME_ARRAY[ArrayIndex.THIRD_INDEX_OF_ARRAY.index], locations.get(
                ArrayIndex.SECOND_INDEX_OF_ARRAY.index)));
        universities.add(getUniversity(NAME_ARRAY[ArrayIndex.FOURTH_INDEX_OF_ARRAY.index], locations.get(
                ArrayIndex.SECOND_INDEX_OF_ARRAY.index)));
        universities.add(getUniversity(NAME_ARRAY[ArrayIndex.FIFTH_INDEX_OF_ARRAY.index], locations.get(
                ArrayIndex.THIRD_INDEX_OF_ARRAY.index)));
        universities.add(getUniversity(NAME_ARRAY[ArrayIndex.SIXTH_INDEX_OF_ARRAY.index], locations.get(
                ArrayIndex.THIRD_INDEX_OF_ARRAY.index)));
        universities.add(getUniversity(NAME_ARRAY[ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index], locations.get(
                ArrayIndex.FOURTH_INDEX_OF_ARRAY.index)));
        universities.add(getUniversity(NAME_ARRAY[ArrayIndex.EIGHTH_INDEX_OF_ARRAY.index], locations.get(
                ArrayIndex.FOURTH_INDEX_OF_ARRAY.index)));
        universities.add(getUniversity(NAME_ARRAY[ArrayIndex.NINTH_INDEX_OF_ARRAY.index], locations.get(
                ArrayIndex.FIFTH_INDEX_OF_ARRAY.index)));
        universities.add(getUniversity(NAME_ARRAY[ArrayIndex.TENTH_INDEX_OF_ARRAY.index], locations.get(
                ArrayIndex.FIFTH_INDEX_OF_ARRAY.index)));
        return universities;
    }

    private static University getUniversity(String name, Location location) {
        University university = new University();
        university.setName(name);
        university.setLocation(location);
        university.setUserProfiles(new ArrayList<>());
        return university;
    }
}
