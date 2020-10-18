package com.senla.carservice.service;

import com.senla.carservice.dao.RoleDao;
import com.senla.carservice.dao.UserDao;
import com.senla.carservice.domain.SystemUser;
import com.senla.carservice.dto.UserDto;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.service.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {
    private static final Long ID_ROLE_USER = 2L;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private BCryptPasswordEncoder cryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        SystemUser systemUser = userDao.findByEmail(email);
        if (systemUser == null) {
            throw new BusinessException("User with email: " + email + " not found");
        } else {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(systemUser.getRole().getName().toString());
            return new User(email, systemUser.getPassword(), List.of(authority));
        }
    }

    public List<UserDto> getSystemUsers() {
        return UserMapper.getUserDto(userDao.getAllRecords());
    }

    @Override
    public boolean saveUser(UserDto userDto) {
        SystemUser systemUser = userDao.findByEmail(userDto.getEmail());
        if (systemUser != null) {
            return false;
        }
        userDto.setPassword(cryptPasswordEncoder.encode(userDto.getPassword()));
        systemUser = UserMapper.getSystemUser(userDto);
        systemUser.setRole(roleDao.findById(ID_ROLE_USER));
        userDao.saveRecord(systemUser);
        return true;
    }
}
