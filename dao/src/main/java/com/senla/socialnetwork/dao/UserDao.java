package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.SystemUser;

public interface UserDao extends GenericDao<SystemUser, Long> {
    SystemUser findByEmail(String email);

}
