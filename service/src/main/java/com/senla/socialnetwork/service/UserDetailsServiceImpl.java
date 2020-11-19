package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.UserDao;
import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.service.exception.BusinessException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        log.debug("[loadUserByUsername]");
        SystemUser systemUser = userDao.findByEmail(email);
        if (systemUser == null) {
            throw new BusinessException("This email does not exist");
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(systemUser.getRole().toString());
        return new User(email, systemUser.getPassword(), List.of(authority));

    }

}
