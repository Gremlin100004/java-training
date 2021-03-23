package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.PublicMessage;
import com.senla.socialnetwork.model.PublicMessage_;
import com.senla.socialnetwork.model.SystemUser;
import com.senla.socialnetwork.model.SystemUser_;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.model.UserProfile_;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

@Repository
public class PublicMessageCriteriaApiDaoImpl extends AbstractDao<PublicMessage, Long> implements PublicMessageDao {
    public PublicMessageCriteriaApiDaoImpl() {
        setType(PublicMessage.class);
    }

    @Override
    public List<PublicMessage> getFriendsMessages(final String email, final int firstResult, final int maxResults) {
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
    }

    @Override
    public List<PublicMessage> findByEmail(final String email, final int firstResult, final int maxResults) {
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
    }

    @Override
    public PublicMessage findByIdAndEmail(final String email, final Long messageId) {
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
    }

}
