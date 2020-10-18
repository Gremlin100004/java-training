package com.senla.carservice.dao;

import com.senla.carservice.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class RoleDaoImpl extends AbstractDao<Role, Long> implements RoleDao {
    public RoleDaoImpl() {
        setType(Role.class);
    }

}
