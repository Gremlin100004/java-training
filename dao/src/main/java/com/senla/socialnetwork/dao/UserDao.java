package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.SystemUser;

public interface UserDao extends GenericDao<SystemUser, Long> {
    SystemUser findByEmail(String email);

}
