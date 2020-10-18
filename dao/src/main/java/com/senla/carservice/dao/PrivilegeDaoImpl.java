package com.senla.carservice.dao;

import com.senla.carservice.domain.Privilege;
import org.springframework.stereotype.Repository;

@Repository
public class PrivilegeDaoImpl extends AbstractDao<Privilege, Long> implements PrivilegeDao {

    public PrivilegeDaoImpl() {
        setType(Privilege.class);
    }

}
