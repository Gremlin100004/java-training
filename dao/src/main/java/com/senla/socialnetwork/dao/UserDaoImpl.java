package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.domain.SystemUser_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
@Slf4j
public class UserDaoImpl extends AbstractDao<SystemUser, Long> implements UserDao {
    private final ConcurrentMap<String, String> logoutToken = new ConcurrentHashMap<>();

    public UserDaoImpl() {
        setType(SystemUser.class);
    }

    @Override
    public SystemUser findByEmail(String email) {
        log.debug("[findByEmail]");
        log.trace("[email: {}]", email);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<SystemUser> criteriaQuery = criteriaBuilder.createQuery(SystemUser.class);
            Root<SystemUser> userRoot = criteriaQuery.from(SystemUser.class);
            criteriaQuery.select(userRoot).distinct(true);
            criteriaQuery.where(criteriaBuilder.equal(userRoot.get(SystemUser_.email), email));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public String getLogoutToken(String email) {
        return logoutToken.get(email);
    }

    @Override
    public void addLogoutToken(String email, String token) {
        logoutToken.putIfAbsent(email, token);
    }

    @Override
    public void deleteLogoutToken(String email) {
        logoutToken.remove(email);
    }

}
