package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.TokenDao;
import com.senla.socialnetwork.dao.UserDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.LogoutToken;
import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.domain.enumaration.RoleName;
import com.senla.socialnetwork.dto.UserForAdminDto;
import com.senla.socialnetwork.dto.UserForSecurityDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.JwtUtil;
import com.senla.socialnetwork.service.util.UserMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private static final int LIST_SIZE = 2;
    private static final int ELEMENT_NUMBER_OF_THE_OBJECT_WITH_OLD_DATA = 0;
    private static final int ELEMENT_NUMBER_OF_THE_OBJECT_WITH_NEW_DATA = 1;
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 0;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TokenDao tokenDao;
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
        log.debug("[getUsers]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return UserMapper.getUserForAdminDto(userDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public UserForSecurityDto getUser(final HttpServletRequest request, final SecretKey secretKey) {
        log.debug("[getUser]");
        log.debug("[request: {}]", request);
        return UserMapper.getUserForSecurityDto(userDao.findByEmail(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey)));
    }

    @Override
    @Transactional
    public String getUserLogoutToken(final String email) {
        log.debug("[getUserLogoutToken]");
        log.debug("[email: {}]", email);
        String token = tokenDao.getLogoutToken(email);
        if (token == null) {
            token = "";
        }
        return token;
    }

    @Override
    @Transactional
    public String logIn(final UserForSecurityDto userDto, final SecretKey secretKey) {
        log.debug("[logIn]");
        log.debug("[userDto: {}]", userDto);
        SystemUser systemUser = userDao.findByEmail(userDto.getEmail());
        if (systemUser == null) {
            throw new BusinessException("This email does not exist");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDto.getEmail(),
            userDto.getPassword());
        authenticationManager.authenticate(authentication);
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(
            systemUser.getRole().toString()));
        User user = new User(userDto.getEmail(), userDto.getPassword(), authorities);
        return JwtUtil.generateToken(user, secretKey, expiration);
    }

    @Override
    @Transactional
    public void logOut(final HttpServletRequest request, final SecretKey secretKey) {
        log.debug("[logOut]");
        log.debug("[request: {}]", request);
        String token = JwtUtil.getToken(request);
        if (token == null) {
            throw new BusinessException("Logout error");
        }
        LogoutToken logoutToken = new LogoutToken();
        logoutToken.setValue(token);
        logoutToken.setSystemUser(userDao.findByEmail(JwtUtil.extractUsername(token, secretKey)));
        tokenDao.saveRecord(logoutToken);
    }

    @Override
    @Transactional
    public void addUser(final UserForSecurityDto userDto, final RoleName roleName) {
        log.debug("[addUser]");
        log.debug("[userDto: {}]", userDto);
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
    public void updateUser(final HttpServletRequest request,
                           final List<UserForSecurityDto> usersDto,
                           final SecretKey secretKey) {
        log.debug("[updateUser]");
        log.debug("[request: {}, usersDto: {}]", request, usersDto);
        if (usersDto.size() != LIST_SIZE) {
            throw new BusinessException("Input data amount error");
        }
        UserForSecurityDto oldUserDto = usersDto.get(ELEMENT_NUMBER_OF_THE_OBJECT_WITH_OLD_DATA);
        UserForSecurityDto newUserDto = usersDto.get(ELEMENT_NUMBER_OF_THE_OBJECT_WITH_NEW_DATA);
        String controlEmail = JwtUtil.extractUsername(JwtUtil.getToken(request), secretKey);
        if (!oldUserDto.getEmail().equals(controlEmail)) {
            throw new BusinessException("login is wrong");
        }
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(oldUserDto.getEmail(), oldUserDto.getPassword()));
        if (!newUserDto.getEmail().equals(controlEmail) && userDao.findByEmail(newUserDto.getEmail()) != null) {
            throw new BusinessException("A user with this email address already exists");
        }
        SystemUser currentSystemUser = userDao.findByEmail(controlEmail);
        LogoutToken logoutToken = new LogoutToken();
        logoutToken.setValue(JwtUtil.getToken(request));
        logoutToken.setSystemUser(currentSystemUser);
        tokenDao.saveRecord(logoutToken);
        UserMapper.getCurrentSystemUser(passwordEncoder, newUserDto, currentSystemUser);
        userDao.updateRecord(currentSystemUser);
    }

    @Override
    @Transactional
    public void deleteUser(final Long userId) {
        log.debug("[deleteUser]");
        log.debug("[userId: {}]", userId);
        SystemUser user = userDao.findById(userId);
        if (user == null) {
            throw new BusinessException("Error, there is no such user");
        }
        userDao.deleteRecord(user);
    }

    @Scheduled(cron = "${cron.expression:0 4 * * * ?}")
    public void cleanLogoutTokens() {
        Date currentDate = new Date();
        List<LogoutToken> logoutTokens = tokenDao.getAllRecords(FIRST_RESULT, MAX_RESULTS);
        logoutTokens.stream()
            .filter(token -> token.getCreationDate().getTime() - currentDate.getTime() > expiration)
            .forEach(token -> tokenDao.deleteRecord(token));
    }

}
