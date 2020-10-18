package com.senla.carservice.service;

import com.senla.carservice.dao.RoleDao;
import com.senla.carservice.dao.UserDao;
import com.senla.carservice.domain.SystemUser;
import com.senla.carservice.dto.UserDto;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.service.util.UserMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private static final Long ID_ROLE_USER = 2L;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private BCryptPasswordEncoder cryptPasswordEncoder;

    @Override
    @Transactional
    public List<UserDto> getSystemUsers() {
        return UserMapper.getUserDto(userDao.getAllRecords());
    }

    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        SystemUser systemUser = userDao.findByEmail(userDto.getEmail());
        if (systemUser != null) {
            throw new BusinessException("A user with this email address already exists");
        }
        userDto.setPassword(cryptPasswordEncoder.encode(userDto.getPassword()));
        systemUser = UserMapper.getSystemUser(userDto);
        systemUser.setRole(roleDao.findById(ID_ROLE_USER));
        return UserMapper.getUserDto(userDao.saveRecord(systemUser));
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        SystemUser user = userDao.findById(userId);
        if (user == null) {
            throw new BusinessException("Error, there is no such user");
        }
        userDao.deleteRecord(userId);
    }

}
