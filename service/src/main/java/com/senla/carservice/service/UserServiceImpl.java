package com.senla.carservice.service;

import com.senla.carservice.dao.RoleDao;
import com.senla.carservice.dao.UserDao;
import com.senla.carservice.domain.SystemUser;
import com.senla.carservice.dto.UserDto;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.service.util.JwtHandler;
import com.senla.carservice.service.util.UserMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private static final Long ID_ROLE_USER = 2L;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private BCryptPasswordEncoder cryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtHandler jwtHandler;

    @Override
    @Transactional
    public List<UserDto> getSystemUsers() {
        return UserMapper.getUserDto(userDao.getAllRecords());
    }

    @Override
    public String logIn(UserDto userDto) {
        log.debug("[logIn]");
        SystemUser systemUser = userDao.findByEmail(userDto.getEmail());
        if (systemUser == null) {
            throw new BusinessException("This email does not exist");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDto.getEmail(),
            userDto.getPassword());
        authenticationManager.authenticate(authentication);
        List<SimpleGrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority(systemUser.getRole().getName().toString()));
        User user = new User(userDto.getEmail(), userDto.getPassword(), authorities);
        return jwtHandler.generateToken(user);
    }

    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        log.debug("[addUser]");
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
        log.debug("[deleteUser]");
        SystemUser user = userDao.findById(userId);
        if (user == null) {
            throw new BusinessException("Error, there is no such user");
        }
        userDao.deleteRecord(userId);
    }

}
