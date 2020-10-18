package com.senla.carservice.dao;

import com.senla.carservice.domain.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role, Long> implements RoleDao {
    public RoleDaoImpl() {
        setType(Role.class);
    }

}
