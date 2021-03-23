package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.PublicMessage;
import com.senla.socialnetwork.model.PublicMessageComment;
import com.senla.socialnetwork.model.PublicMessageComment_;
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
import java.util.List;

@Repository
public class PublicMessageCommentCriteriaApiDaoImpl extends AbstractDao<PublicMessageComment, Long>
    implements PublicMessageCommentDao {
    public PublicMessageCommentCriteriaApiDaoImpl() {
        setType(PublicMessageComment.class);
    }

    @Override
    public PublicMessageComment findByIdAndEmail(final String email, final Long commentId) {
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
    }

    @Override
    public List<PublicMessageComment> getPublicMessageComments(final Long publicMessageId,
                                                               final int firstResult,
                                                               final int maxResults) {
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
    }

}
