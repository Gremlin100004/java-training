package com.senla.socialnetwork.dao.testdata;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.*;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
public class UserProfileTestData {
    private static final SimpleDateFormat TIME_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final List<String> NAME_LIST = Arrays.asList(
            "Petya",
            "Artem",
            "Kiril",
            "Vasya");
    private static final List<String> SURNAME_LIST = Arrays.asList(
            "Buhmetovich",
            "Karchevskiy",
            "Myagkov",
            "Potryopovich");
    private static final List<String> TELEPHONE_NUMBER_LIST = Arrays.asList(
            "+375(29)766-54-23",
            "+375(29)467-34-62",
            "+375(29)123-41-67",
            "+375(33)567-34-09");
    private static final List<Date> REGISTRATION_DATES = Arrays.asList(
            getDateTime("2020-07-11 10:00"),
            getDateTime("2020-08-11 10:00"),
            getDateTime("2020-09-11 10:00"),
            getDateTime("2019-02-05 09:53")
    );
    private static final List<Date> BIRTHDAY_DAYS = Arrays.asList(
            getDate("1990-07-28"),
            getDate("2004-11-12"),
            getDate("1996-03-21"),
            getDate("2001-03-11")
    );
    private static final List<Integer> GRADUATED_SCHOOL_DATES = Arrays.asList(
            2007, 2007, 2007, 2010
    );
    private static final List<Integer> GRADUATED_UNIVERSITY_DATES = Arrays.asList(
            2013, 2013, 2013, 2015
    );

    public static UserProfile getUserProfile(SystemUser systemUser,
                                             Date registrationDate,
                                             Date dateOfBirthday,
                                             String name,
                                             String surname,
                                             String telephoneNumber,
                                             Location location,
                                             School school,
                                             int schoolGraduationYear,
                                             University university,
                                             int universityGraduationYear) {
        UserProfile userProfile = new UserProfile();
        userProfile.setSystemUser(systemUser);
        userProfile.setRegistrationDate(registrationDate);
        userProfile.setDateOfBirth(dateOfBirthday);
        userProfile.setName(name);
        userProfile.setSurname(surname);
        userProfile.setTelephoneNumber(telephoneNumber);
        userProfile.setLocation(location);
        userProfile.setSchool(school);
        userProfile.setSchoolGraduationYear(schoolGraduationYear);
        userProfile.setUniversity(university);
        userProfile.setUniversityGraduationYear(universityGraduationYear);
        userProfile.setCommunitiesSubscribedTo(new ArrayList<>());
        userProfile.setOwnCommunities(new ArrayList<>());
        userProfile.setPublicMessages(new ArrayList<>());
        return userProfile;
    }

    public static List<UserProfile> getUsersProfiles(List<Location> locations,
                                                     List<School> schools,
                                                     List<University> universities,
                                                     List<SystemUser> systemUsers) {
        List<UserProfile> userProfiles = new ArrayList<>();
        userProfiles.add(getUserProfile(
                systemUsers.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                REGISTRATION_DATES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                BIRTHDAY_DAYS.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                NAME_LIST.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                SURNAME_LIST.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                TELEPHONE_NUMBER_LIST.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                locations.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                schools.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                GRADUATED_SCHOOL_DATES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                universities.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index),
                GRADUATED_UNIVERSITY_DATES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index)
                ));
        userProfiles.add(getUserProfile(
                systemUsers.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                REGISTRATION_DATES.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                BIRTHDAY_DAYS.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                NAME_LIST.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                SURNAME_LIST.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                TELEPHONE_NUMBER_LIST.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                locations.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                schools.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                GRADUATED_SCHOOL_DATES.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index),
                universities.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                GRADUATED_UNIVERSITY_DATES.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index)
        ));
        userProfiles.add(getUserProfile(
                systemUsers.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                REGISTRATION_DATES.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                BIRTHDAY_DAYS.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                NAME_LIST.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                SURNAME_LIST.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                TELEPHONE_NUMBER_LIST.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                locations.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                schools.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
                GRADUATED_SCHOOL_DATES.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index),
                universities.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                GRADUATED_UNIVERSITY_DATES.get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index)
        ));
        userProfiles.add(getUserProfile(
                systemUsers.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                REGISTRATION_DATES.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                BIRTHDAY_DAYS.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                NAME_LIST.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                SURNAME_LIST.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                TELEPHONE_NUMBER_LIST.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                locations.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                schools.get(ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index),
                GRADUATED_SCHOOL_DATES.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index),
                universities.get(ArrayIndex.FIFTH_INDEX_OF_ARRAY.index),
                GRADUATED_UNIVERSITY_DATES.get(ArrayIndex.FOURTH_INDEX_OF_ARRAY.index)
        ));
        return userProfiles;
    }

    public static Date getDateTime(String date){
        try {
            return TIME_DATE_FORMATTER.parse(date);
        } catch (ParseException exception) {
            log.error(exception.getMessage());
            return null;
        }
    }

    public static Date getDate(String date){
        try {
            return DATE_FORMATTER.parse(date);
        } catch (ParseException exception) {
            log.error(exception.getMessage());
            return null;
        }
    }

}
