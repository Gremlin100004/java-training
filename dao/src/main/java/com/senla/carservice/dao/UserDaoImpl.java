package com.senla.carservice.dao;

import com.senla.carservice.domain.SystemUser;
import com.senla.carservice.domain.SystemUser_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
@Slf4j
public class UserDaoImpl extends AbstractDao<SystemUser, Long> implements UserDao {
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

}
