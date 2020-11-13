package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.domain.PostComment;
import com.senla.socialnetwork.domain.PostComment_;
import com.senla.socialnetwork.domain.Post_;
import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.domain.PublicMessageComment_;
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
public class PostCommentDaoImpl extends AbstractDao<PostComment, Long> implements PostCommentDao {
    public PostCommentDaoImpl() {
        setType(PostComment.class);
    }

    @Override
    public PostComment findByIdAndEmail(String email, Long commentId) {
        log.debug("[findByIdAndEmail]");
        log.trace("[email: {}, commentId: {}]", email, commentId);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PostComment> criteriaQuery = criteriaBuilder.createQuery(PostComment.class);
            Root<PostComment> postCommentRoot = criteriaQuery.from(PostComment.class);
            Join<PostComment, UserProfile> postCommentUserProfileJoin = postCommentRoot.join(PostComment_.author);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = postCommentUserProfileJoin.join(
                UserProfile_.systemUser);
            criteriaQuery.select(postCommentRoot);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(
                userProfileSystemUserJoin.get(SystemUser_.email), email)), criteriaBuilder.equal(
                postCommentRoot.get(PublicMessageComment_.id), commentId));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<PostComment> getPostComments(Long postId, int firstResult, int maxResults) {
        log.debug("[getPostComments]");
        log.trace("[postId: {}, firstResult: {}, maxResults: {}]", postId, firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PostComment> criteriaQuery = criteriaBuilder.createQuery(PostComment.class);
            Root<PostComment> postCommentRoot = criteriaQuery.from(PostComment.class);
            Join<PostComment, Post> postCommentPostJoin = postCommentRoot.join(PostComment_.post);
            criteriaQuery.select(postCommentRoot);
            criteriaQuery.where(criteriaBuilder.equal(postCommentPostJoin.get(Post_.id), postId));
            TypedQuery<PostComment> typedQuery = entityManager.createQuery(criteriaQuery);
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
