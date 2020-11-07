package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.UserProfile;

public interface UserProfileDao extends GenericDao<UserProfile, Long> {
    Location getLocation(String email);

}
