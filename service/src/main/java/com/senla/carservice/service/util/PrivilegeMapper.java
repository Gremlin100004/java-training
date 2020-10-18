package com.senla.carservice.service.util;

import com.senla.carservice.dao.PrivilegeDao;
import com.senla.carservice.domain.Privilege;
import com.senla.carservice.domain.Role;
import com.senla.carservice.dto.PrivilegeDto;
import com.senla.carservice.dto.RoleDto;

public class PrivilegeMapper {

    public static Privilege getPrivilege(PrivilegeDto privilegeDto, PrivilegeDao privilegeDao){
        Privilege privilege;
        if (privilegeDto.getId() == null) {
            privilege = new Privilege();
        } else {
            privilege = privilegeDao.findById(privilegeDto.getId());
        }
        privilege.setName(privilegeDto.getName());
        return privilege;
    }
}
