package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.domain.SystemUser_;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.domain.UserProfile_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

@Repository
@Slf4j
public class LocationDaoImpl extends AbstractDao<Location, Long> implements LocationDao {
    public LocationDaoImpl() {
        setType(Location.class);
    }

    @Override
    public Location getLocation(String email) {
        log.debug("[getLocation]");
        log.trace("[email: {}]", email);
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
