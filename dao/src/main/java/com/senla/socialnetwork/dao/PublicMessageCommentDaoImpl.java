package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.domain.PublicMessageComment_;
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
import java.util.List;

@Repository
@Slf4j
public class PublicMessageCommentDaoImpl extends AbstractDao<PublicMessageComment, Long> implements PublicMessageCommentDao {
    public PublicMessageCommentDaoImpl() {
        setType(PublicMessageComment.class);
    }

    @Override
    public PublicMessageComment findByIdAndEmail(String email, Long commentId) {
        log.debug("[findByIdAndEmail]");
        log.trace("[email: {}, commentId: {}]", email, commentId);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PublicMessageComment> criteriaQuery = criteriaBuilder.createQuery(PublicMessageComment.class);
            Root<PublicMessageComment> publicMessageCommentRoot = criteriaQuery.from(PublicMessageComment.class);
            Join<PublicMessageComment, UserProfile> publicMessageUserProfileJoin = publicMessageCommentRoot.join(
                PublicMessageComment_.author);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = publicMessageUserProfileJoin.join(
                UserProfile_.systemUser);
            criteriaQuery.select(publicMessageCommentRoot);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(
                userProfileSystemUserJoin.get(SystemUser_.email), email)), criteriaBuilder.equal(
                publicMessageCommentRoot.get(PublicMessageComment_.id), commentId));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<PublicMessageComment> getPublicMessageComments(Long publicMessageId, int firstResult, int maxResults) {
        log.debug("[getPublicMessageComments]");
        log.trace("[publicMessageId: {}]", publicMessageId);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PublicMessageComment> criteriaQuery = criteriaBuilder.createQuery(PublicMessageComment.class);
            Root<PublicMessageComment> publicMessageCommentRoot = criteriaQuery.from(PublicMessageComment.class);
            Join<PublicMessageComment, PublicMessage> publicMessageCommentPublicMessageJoin = publicMessageCommentRoot.join(
                PublicMessageComment_.publicMessage);
            criteriaQuery.select(publicMessageCommentRoot);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(
                publicMessageCommentPublicMessageJoin.get(PublicMessage_.id), publicMessageId), criteriaBuilder.equal(
                    publicMessageCommentRoot.get(PublicMessageComment_.isDeleted), false)));
            TypedQuery<PublicMessageComment> typedQuery = entityManager.createQuery(criteriaQuery);
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

}
