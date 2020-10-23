package com.senla.carservice.dao;

import com.senla.carservice.domain.SystemUser;

public interface UserDao extends GenericDao<SystemUser, Long> {
    SystemUser findByEmail(String email);

}
