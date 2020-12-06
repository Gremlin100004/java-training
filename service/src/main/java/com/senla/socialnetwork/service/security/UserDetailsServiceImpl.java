package com.senla.socialnetwork.service.security;

import com.senla.socialnetwork.dao.UserDao;
import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.service.exception.BusinessException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@NoArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        log.debug("[email: {}]", email);
        SystemUser systemUser = userDao.findByEmail(email);
        if (systemUser == null) {
            throw new BusinessException("This user does not exist");
        }
        return new UserPrincipal(systemUser);
    }

}
