package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.domain.PublicMessage_;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.domain.UserProfile_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

@Repository
@Slf4j
public class PublicMessageDaoImpl extends AbstractDao<PublicMessage, Long> implements PublicMessageDao {
    public PublicMessageDaoImpl() {
        setType(PublicMessage.class);
    }
    //ToDo Add entity friend and request friends
    @Override
    public List<PublicMessage> getFriendsMessages(UserProfile ownProfile, int firstResult, int maxResults) {
        log.debug("[getFriendsMessages]");
        log.trace("[ownProfile: {}]", ownProfile);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PublicMessage> criteriaQuery = criteriaBuilder.createQuery(PublicMessage.class);
            Root<PublicMessage> publicMessageRoot = criteriaQuery.from(PublicMessage.class);
            Subquery<UserProfile> userProfileSubquery = criteriaQuery.subquery(UserProfile.class);
            Root<UserProfile> userProfileRoot = userProfileSubquery.from(UserProfile.class);
            Join<UserProfile, UserProfile> profileUserProfileJoin = userProfileRoot.join(UserProfile_.friends);
            userProfileSubquery.select(userProfileSubquery);
            userProfileSubquery.where(criteriaBuilder.equal(profileUserProfileJoin.get(UserProfile_.id), ownProfile.getId()));
            criteriaQuery.select(publicMessageRoot);
//            criteriaQuery.where(criteriaBuilder.equal(publicMessageRoot.get(PublicMessage_.author), userProfileSubquery));

//            Predicate predicateFriends = criteriaBuilder.equal(profileUserProfileJoin.get(UserProfile_.id), ownProfile.getId());
//            Predicate predicateAuthor = criteriaBuilder.equal(
//                publicMessageUserProfileJoin.get(PublicMessage_.author), ownProfile);
            Predicate predicateDeleted = criteriaBuilder.equal(
                publicMessageRoot.get(PublicMessage_.isDeleted), false);
//            criteriaQuery.where(criteriaBuilder.and(predicateFriends, predicateDeleted));
            criteriaQuery.orderBy(criteriaBuilder.asc(publicMessageRoot.get(PublicMessage_.creationDate)));
            TypedQuery<PublicMessage> typedQuery = entityManager.createQuery(criteriaQuery);
//            typedQuery.setFirstResult(firstResult);
//            typedQuery.setMaxResults(maxResults);
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

}
