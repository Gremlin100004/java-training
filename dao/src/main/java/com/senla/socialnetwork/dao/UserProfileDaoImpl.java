package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.UserProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UserProfileDaoImpl extends AbstractDao<UserProfile, Long> implements UserProfileDao {
    public UserProfileDaoImpl() {
        setType(UserProfile.class);
    }

}
