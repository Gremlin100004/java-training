package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.SystemUser;
import com.senla.socialnetwork.model.SystemUser_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserCriteriaApiDaoImpl extends AbstractDao<SystemUser, Long> implements UserDao {
    public UserCriteriaApiDaoImpl() {
        setType(SystemUser.class);
    }

    @Override
    public SystemUser findByEmail(final String email) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<SystemUser> criteriaQuery = criteriaBuilder.createQuery(SystemUser.class);
            Root<SystemUser> userRoot = criteriaQuery.from(SystemUser.class);
            criteriaQuery.select(userRoot).distinct(true);
            criteriaQuery.where(criteriaBuilder.equal(userRoot.get(SystemUser_.email), email));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
