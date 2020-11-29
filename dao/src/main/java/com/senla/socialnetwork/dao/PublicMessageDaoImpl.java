package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.domain.PublicMessage_;
import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.domain.SystemUser_;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.domain.UserProfile_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

@Repository
@Slf4j
public class PublicMessageDaoImpl extends AbstractDao<PublicMessage, Long> implements PublicMessageDao {
    public PublicMessageDaoImpl() {
        setType(PublicMessage.class);
    }

    @Override
    public List<PublicMessage> getFriendsMessages(final String email, final int firstResult, final int maxResults) {
        log.debug("[getFriendsMessages]");
        log.trace("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PublicMessage> criteriaQuery = criteriaBuilder.createQuery(PublicMessage.class);
            Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
            Root<UserProfile> userProfileRoot = subquery.from(UserProfile.class);
            Join<UserProfile, UserProfile> userProfileUserProfileJoin = userProfileRoot.join(UserProfile_.friends);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = userProfileRoot.join(UserProfile_.systemUser);
            subquery.select(userProfileUserProfileJoin.get(UserProfile_.id)).distinct(true);
            subquery.where(criteriaBuilder.equal(userProfileSystemUserJoin.get(SystemUser_.email), email));
            Root<PublicMessage> publicMessageRoot = criteriaQuery.from(PublicMessage.class);
            Join<PublicMessage, UserProfile> publicMessageUserProfileJoin = publicMessageRoot.join(PublicMessage_.author);
            criteriaQuery.select(publicMessageRoot);
            criteriaQuery.where(criteriaBuilder.and(publicMessageUserProfileJoin.get(
                UserProfile_.id).in(subquery), criteriaBuilder.equal(publicMessageRoot.get(
                    PublicMessage_.isDeleted), false)));
            criteriaQuery.orderBy(criteriaBuilder.asc(publicMessageRoot.get(PublicMessage_.creationDate)));
            TypedQuery<PublicMessage> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<PublicMessage> getByEmail(final String email, final int firstResult, final int maxResults) {
        log.debug("[getByEmail]");
        log.trace("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PublicMessage> criteriaQuery = criteriaBuilder.createQuery(PublicMessage.class);
            Root<PublicMessage> publicMessageRoot = criteriaQuery.from(PublicMessage.class);
            Join<PublicMessage, UserProfile> publicMessageUserProfileJoin = publicMessageRoot.join(PublicMessage_.author);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = publicMessageUserProfileJoin.join(UserProfile_.systemUser);
            criteriaQuery.select(publicMessageRoot);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(userProfileSystemUserJoin.get(
                SystemUser_.email), email), criteriaBuilder.equal(
                    publicMessageRoot.get(PublicMessage_.isDeleted), false)));
            criteriaQuery.orderBy(criteriaBuilder.asc(publicMessageRoot.get(PublicMessage_.creationDate)));
            TypedQuery<PublicMessage> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public PublicMessage findByIdAndEmail(final String email, final Long messageId) {
        log.debug("[findByIdAndEmail]");
        log.trace("[email: {}, messageId: {}]", email, messageId);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PublicMessage> criteriaQuery = criteriaBuilder.createQuery(PublicMessage.class);
            Root<PublicMessage> publicMessageRoot = criteriaQuery.from(PublicMessage.class);
            Join<PublicMessage, UserProfile> publicMessageUserProfileJoin = publicMessageRoot.join(PublicMessage_.author);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = publicMessageUserProfileJoin.join(
                UserProfile_.systemUser);
            criteriaQuery.select(publicMessageRoot);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(
                userProfileSystemUserJoin.get(SystemUser_.email), email)), criteriaBuilder.equal(
                    publicMessageRoot.get(PublicMessage_.id), messageId));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

}
