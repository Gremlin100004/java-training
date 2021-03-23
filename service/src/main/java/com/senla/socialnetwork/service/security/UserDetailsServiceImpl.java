package com.senla.socialnetwork.service.security;

import com.senla.socialnetwork.aspect.ServiceLog;
import com.senla.socialnetwork.dao.UserDao;
import com.senla.socialnetwork.model.SystemUser;
import com.senla.socialnetwork.service.exception.BusinessException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@ServiceLog
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        SystemUser systemUser = userDao.findByEmail(email);
        if (systemUser == null) {
            throw new BusinessException("This user does not exist");
        }
        return new UserPrincipal(systemUser);
    }

}
