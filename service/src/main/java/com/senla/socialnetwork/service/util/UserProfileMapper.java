package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.UserProfileDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserProfileMapper {
    public static UserProfileDto getUserProfileDto(final UserProfile userProfile) {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId(userProfile.getId());
        userProfileDto.setRegistrationDate(userProfile.getRegistrationDate());
        if (userProfile.getDateOfBirth() != null) {
            userProfileDto.setDateOfBirth(userProfile.getDateOfBirth());
        }
        if (userProfile.getName() != null) {
            userProfileDto.setName(userProfile.getName());
        }
        if (userProfile.getSurname() != null) {
            userProfileDto.setSurname(userProfile.getSurname());
        }
        if (userProfile.getTelephone_number() != null) {
            userProfileDto.setTelephone_number(userProfile.getTelephone_number());
        }
        if (userProfile.getLocation() != null) {
            userProfileDto.setLocation(LocationMapper.getLocationDto(userProfile.getLocation()));
        }
        if (userProfile.getSchool() != null) {
            userProfileDto.setSchool(SchoolMapper.getSchoolDto(userProfile.getSchool()));
        }
        if (userProfile.getSchoolGraduationYear() != null) {
            userProfileDto.setSchoolGraduationYear(userProfile.getSchoolGraduationYear());
        }
        if (userProfile.getUniversity() != null) {
            userProfileDto.setUniversity(UniversityMapper.getUniversityDto(userProfile.getUniversity()));
        }
        if (userProfile.getSchoolGraduationYear() != null) {
            userProfileDto.setUniversityGraduationYear(userProfile.getSchoolGraduationYear());
        }
        return userProfileDto;
    }

    public static UserProfile getUserProfile(final UserProfileDto userProfileDto,
                                             final UserProfileDao userProfileDao,
                                             final LocationDao locationDao,
                                             final SchoolDao schoolDao,
                                             final UniversityDao universityDao) {
        UserProfile userProfile;
        if (userProfileDto.getId() == null) {
            userProfile = new UserProfile();
        } else {
            userProfile = userProfileDao.findById(userProfileDto.getId());
        }
        userProfile.setRegistrationDate(userProfileDto.getRegistrationDate());
        if (userProfileDto.getDateOfBirth() != null) {
            userProfile.setDateOfBirth(userProfileDto.getDateOfBirth());
        }
        if (userProfileDto.getName() != null) {
            userProfile.setName(userProfileDto.getName());
        }
        if (userProfileDto.getSurname() != null) {
            userProfile.setSurname(userProfileDto.getSurname());
        }
        if (userProfileDto.getTelephone_number() != null) {
            userProfile.setTelephone_number(userProfileDto.getTelephone_number());
        }
        if (userProfileDto.getLocation() != null) {
            userProfile.setLocation(LocationMapper.getLocation(userProfileDto.getLocation(), locationDao));
        }
        if (userProfileDto.getSchool() != null) {
            userProfile.setSchool(SchoolMapper.getSchool(userProfileDto.getSchool(), schoolDao, locationDao));
        }
        if (userProfileDto.getSchoolGraduationYear() != null) {
            userProfile.setSchoolGraduationYear(userProfileDto.getSchoolGraduationYear());
        }
        if (userProfileDto.getUniversity() != null) {
            userProfile.setUniversity(UniversityMapper.getUniversity(userProfileDto.getUniversity(), universityDao, locationDao));
        }
        if (userProfileDto.getUniversityGraduationYear() != null) {
            userProfile.setUniversityGraduationYear(userProfileDto.getUniversityGraduationYear());
        }
        return userProfile;
    }

    public static List<UserProfileDto> getUserProfileDto(final List<UserProfile> userProfiles) {
        return userProfiles.stream()
            .map(UserProfileMapper::getUserProfileDto)
            .collect(Collectors.toList());
    }

}
