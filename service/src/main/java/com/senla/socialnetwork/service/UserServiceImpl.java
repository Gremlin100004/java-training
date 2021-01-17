package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.UserDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.domain.enumaration.RoleName;
import com.senla.socialnetwork.dto.UserForAdminDto;
import com.senla.socialnetwork.dto.UserForSecurityDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.UserMapper;
import com.senla.socialnetwork.service.security.UserPrincipal;
import com.senla.socialnetwork.service.util.JwtUtil;
import com.senla.socialnetwork.service.util.PrincipalUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private static final int LIST_SIZE = 2;
    private static final int ELEMENT_NUMBER_OF_THE_OBJECT_WITH_OLD_DATA = 0;
    private static final int ELEMENT_NUMBER_OF_THE_OBJECT_WITH_NEW_DATA = 1;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Value("${com.senla.socialnetwork.controller.JwtUtil.expiration:3600000}")
    private Integer expiration;

    @Override
    @Transactional
    public List<UserForAdminDto> getUsers(final int firstResult, final int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return UserMapper.getUserForAdminDto(userDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public UserForSecurityDto getUser() {
        return UserMapper.getUserForSecurityDto(userDao.findByEmail(PrincipalUtil.getUserName()));
    }

    @Override
    @Transactional
    public String logIn(final UserForSecurityDto userDto, final SecretKey secretKey) {
        log.debug("[userDto: {}]", userDto);
        SystemUser systemUser = userDao.findByEmail(userDto.getEmail());
        if (systemUser == null) {
            throw new BusinessException("This email does not exist");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            userDto.getEmail(), userDto.getPassword()));
        return JwtUtil.generateToken(new UserPrincipal(systemUser), secretKey, expiration);
    }

    @Override
    @Transactional
    public void addUser(final UserForSecurityDto userDto, final RoleName roleName) {
        log.debug("[userDto: {}, roleName: {}]", userDto, roleName);
        SystemUser systemUser = userDao.findByEmail(userDto.getEmail());
        if (systemUser != null) {
            throw new BusinessException("A user with this email address already exists");
        }
        systemUser = UserMapper.getSystemUser(passwordEncoder, userDto, roleName);
        systemUser = userDao.saveRecord(systemUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setSystemUser(systemUser);
        userProfile.setRegistrationDate(new Date());
        userProfileDao.saveRecord(userProfile);
    }

    @Override
    @Transactional
    public void updateUser(final List<UserForSecurityDto> usersDto) {
        log.debug("[usersDto: {}]", usersDto);
        if (usersDto.size() != LIST_SIZE) {
            throw new BusinessException("Input data amount error");
        }
        UserForSecurityDto oldUserDto = usersDto.get(ELEMENT_NUMBER_OF_THE_OBJECT_WITH_OLD_DATA);
        UserForSecurityDto newUserDto = usersDto.get(ELEMENT_NUMBER_OF_THE_OBJECT_WITH_NEW_DATA);
        String controlEmail = PrincipalUtil.getUserName();
        if (!oldUserDto.getEmail().equals(controlEmail)) {
            throw new BusinessException("login is wrong");
        }
        if (!newUserDto.getEmail().equals(controlEmail) && userDao.findByEmail(newUserDto.getEmail()) != null) {
            throw new BusinessException("A user with this email address already exists");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            oldUserDto.getEmail(), oldUserDto.getPassword()));
        SystemUser currentSystemUser = userDao.findByEmail(controlEmail);
        UserMapper.getCurrentSystemUser(passwordEncoder, newUserDto, currentSystemUser);
        userDao.updateRecord(currentSystemUser);
    }

    @Override
    @Transactional
    public void deleteUser(final Long userId) {
        log.debug("[userId: {}]", userId);
        SystemUser user = userDao.findById(userId);
        if (user == null) {
            throw new BusinessException("Error, there is no such user");
        }
        userDao.deleteRecord(user);
    }

}
