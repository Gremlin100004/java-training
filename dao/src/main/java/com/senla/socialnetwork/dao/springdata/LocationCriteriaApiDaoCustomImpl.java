package com.senla.socialnetwork.dao.springdata;

import com.senla.socialnetwork.model.Location;
import com.senla.socialnetwork.model.SystemUser;
import com.senla.socialnetwork.model.SystemUser_;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.model.UserProfile_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

@Repository
@Slf4j
public class LocationCriteriaApiDaoCustomImpl implements LocationCriteriaApiDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Location getLocation(final String email) {
        log.debug("[email: {}]", email);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Location> criteriaQuery = criteriaBuilder.createQuery(Location.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            Join<UserProfile, SystemUser> masterOrderJoin = userProfileRoot.join(UserProfile_.systemUser);
            criteriaQuery.select(userProfileRoot.get(UserProfile_.location));
            criteriaQuery.where(criteriaBuilder.equal(masterOrderJoin.get(SystemUser_.email), email));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

}
