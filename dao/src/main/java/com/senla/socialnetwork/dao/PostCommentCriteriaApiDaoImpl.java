package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.Post;
import com.senla.socialnetwork.model.PostComment;
import com.senla.socialnetwork.model.PostComment_;
import com.senla.socialnetwork.model.Post_;
import com.senla.socialnetwork.model.PublicMessageComment_;
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
public class PostCommentCriteriaApiDaoImpl extends AbstractDao<PostComment, Long> implements PostCommentDao {
    public PostCommentCriteriaApiDaoImpl() {
        setType(PostComment.class);
    }

    @Override
    public PostComment findByIdAndEmail(final String email, final Long commentId) {
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
    }

    @Override
    public List<PostComment> getPostComments(final Long postId, final int firstResult, final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<PostComment> criteriaQuery = criteriaBuilder.createQuery(PostComment.class);
            Root<PostComment> postCommentRoot = criteriaQuery.from(PostComment.class);
            Join<PostComment, Post> postCommentPostJoin = postCommentRoot.join(PostComment_.post);
            criteriaQuery.select(postCommentRoot);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(
                postCommentPostJoin.get(Post_.id), postId), criteriaBuilder.equal(
                    postCommentPostJoin.get(Post_.isDeleted), false)));
            TypedQuery<PostComment> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

}
