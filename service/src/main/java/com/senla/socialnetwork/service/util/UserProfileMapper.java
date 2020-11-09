package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.UserProfileDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserProfileMapper {
    public static UserProfileDto getUserProfileDto(UserProfile userProfile) {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId(userProfile.getId());
        userProfileDto.setRegistrationDate(userProfile.getRegistrationDate());
        userProfileDto.setDateOfBirth(userProfile.getDateOfBirth());
        userProfileDto.setName(userProfile.getName());
        userProfileDto.setSurname(userProfile.getSurname());
        userProfileDto.setTelephone_number(userProfile.getTelephone_number());
        userProfileDto.setLocation(LocationMapper.getLocationDto(userProfile.getLocation()));
        userProfileDto.setSchool(SchoolMapper.getSchoolDto(userProfile.getSchool()));
        userProfileDto.setSchoolGraduationYear(userProfile.getSchoolGraduationYear());
        userProfileDto.setUniversity(UniversityMapper.getUniversityDto(userProfile.getUniversity()));
        userProfileDto.setUniversityGraduationYear(userProfile.getSchoolGraduationYear());
        return userProfileDto;
    }

    public static UserProfile getUserProfile(UserProfileDto userProfileDto,
                                             UserProfileDao userProfileDao,
                                             LocationDao locationDao,
                                             SchoolDao schoolDao,
                                             UniversityDao universityDao) {
        UserProfile userProfile;
        if (userProfileDto.getId() == null) {
            userProfile = new UserProfile();
        } else {
            userProfile = userProfileDao.findById(userProfileDto.getId());
        }
        userProfile.setRegistrationDate(userProfileDto.getRegistrationDate());
        userProfile.setDateOfBirth(userProfileDto.getDateOfBirth());
        userProfile.setName(userProfileDto.getName());
        userProfile.setSurname(userProfileDto.getSurname());
        userProfile.setTelephone_number(userProfileDto.getTelephone_number());
        userProfile.setLocation(LocationMapper.getLocation(userProfileDto.getLocation(), locationDao));
        userProfile.setSchool(SchoolMapper.getSchool(userProfileDto.getSchool(), schoolDao, locationDao));
        userProfile.setSchoolGraduationYear(userProfileDto.getSchoolGraduationYear());
        userProfile
            .setUniversity(UniversityMapper.getUniversity(userProfileDto.getUniversity(), universityDao, locationDao));
        userProfile.setUniversityGraduationYear(userProfileDto.getUniversityGraduationYear());
        return userProfile;
    }

    public static List<UserProfile> getUserProfile(List<UserProfileDto> userProfilesDto,
                                                   UserProfileDao userProfileDao,
                                                   LocationDao locationDao,
                                                   SchoolDao schoolDao,
                                                   UniversityDao universityDao) {
        List<UserProfile> userProfiles = new ArrayList<>();
        for (UserProfileDto userProfileDto : userProfilesDto) {
            UserProfile userProfile =
                getUserProfile(userProfileDto, userProfileDao, locationDao, schoolDao, universityDao);
            userProfiles.add(userProfile);
        }
        return userProfiles;
    }

    public static List<UserProfileDto> getUserProfileDto(List<UserProfile> userProfiles) {
        return userProfiles.stream()
            .map(UserProfileMapper::getUserProfileDto)
            .collect(Collectors.toList());
    }

}
