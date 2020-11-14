package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.UserDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.domain.enumaration.RoleName;
import com.senla.socialnetwork.dto.UserDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.UserMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserProfileDao userProfileDao;
//    @Autowired
//    private BCryptPasswordEncoder cryptPasswordEncoder;
//    @Autowired
//    private AuthenticationManager authenticationManager;
    @Value("${com.senla.carservice.JwtUtil.secret-key:qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq}")
    private String secretKey;
    @Value("${com.senla.carservice.controller.JwtUtil.expiration:3600000}")
    private Integer expiration;

    @Override
    @Transactional
    public List<UserDto> getUsers(int firstResult, int maxResults) {
        log.debug("[getUsers]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return UserMapper.getUserDto(userDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public UserDto getUser() {
        log.debug("[getUser]");
//        String email = authentication.getName();
        String email = "";
        return UserMapper.getUserDto(userDao.findByEmail(email));
    }

//    @Override
//    public String logIn(UserDto userDto) {
//        log.debug("[logIn]");
//        SystemUser systemUser = userDao.findByEmail(userDto.getEmail());
//        if (systemUser == null) {
//            throw new BusinessException("This email does not exist");
//        }
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDto.getEmail(),
//                                                                                                     userDto.getPassword());
//        authenticationManager.authenticate(authentication);
//        List<SimpleGrantedAuthority> authorities = List.of(
//            new SimpleGrantedAuthority(systemUser.getRole().toString()));
//        User user = new User(userDto.getEmail(), userDto.getPassword(), authorities);
//        return JwtUtil.generateToken(user, secretKey, expiration);
//    }

    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        log.debug("[addUser]");
        log.debug("[userDto: {}]", userDto);
        if (userDto == null) {
            throw new BusinessException("Error, null user");
        }
        SystemUser systemUser = userDao.findByEmail(userDto.getEmail());
        if (systemUser != null) {
            throw new BusinessException("A user with this email address already exists");
        }
//        userDto.setPassword(cryptPasswordEncoder.encode(userDto.getPassword()));
        systemUser = UserMapper.getSystemUser(userDto);
        systemUser.setRole(RoleName.ROLE_USER);
        SystemUser savedSystemUser = userDao.saveRecord(systemUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setSystemUser(savedSystemUser);
        userProfile.setRegistrationDate(new Date());
        userProfileDao.saveRecord(userProfile);
        return UserMapper.getUserDto(savedSystemUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        log.debug("[deleteUser]");
        log.debug("[userId: {}]", userId);
        if (userDao.findById(userId) == null) {
            throw new BusinessException("Error, there is no such user");
        }
        userDao.deleteRecord(userId);
    }

}
