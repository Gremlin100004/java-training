package com.senla.carservice.service.util;

import com.senla.carservice.dao.RoleDao;
import com.senla.carservice.domain.Role;
import com.senla.carservice.domain.SystemUser;
import com.senla.carservice.dto.RoleDto;
import com.senla.carservice.dto.UserDto;

public class RoleMapper {

    public static Role getRole(RoleDto roleDto, RoleDao roleDao){
        Role role;
        if (roleDto.getId() == null){
            role = new Role();
        } else {
            role = roleDao.findById(roleDto.getId());
        }
        role.setName(roleDto.getName());
        return role;
    }
}
