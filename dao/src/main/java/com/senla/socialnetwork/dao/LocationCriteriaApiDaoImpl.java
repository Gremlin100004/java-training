package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.Location;
import com.senla.socialnetwork.model.SystemUser;
import com.senla.socialnetwork.model.SystemUser_;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.model.UserProfile_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

@Repository
public class LocationCriteriaApiDaoImpl extends AbstractDao<Location, Long> implements LocationDao {
    public LocationCriteriaApiDaoImpl() {
        setType(Location.class);
    }

    @Override
    public Location getLocation(final String email) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Location> criteriaQuery = criteriaBuilder.createQuery(Location.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            Join<UserProfile, SystemUser> masterOrderJoin = userProfileRoot.join(UserProfile_.systemUser);
            criteriaQuery.select(userProfileRoot.get(UserProfile_.location));
            criteriaQuery.where(criteriaBuilder.equal(masterOrderJoin.get(SystemUser_.email), email));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
