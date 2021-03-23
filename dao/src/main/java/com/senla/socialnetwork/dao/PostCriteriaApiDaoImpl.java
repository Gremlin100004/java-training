package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.Community_;
import com.senla.socialnetwork.model.Post;
import com.senla.socialnetwork.model.Post_;
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
public class PostCriteriaApiDaoImpl extends AbstractDao<Post, Long> implements PostDao {
    public PostCriteriaApiDaoImpl() {
        setType(Post.class);
    }

    @Override
    public Post findByIdAndEmail(final String email, final Long postId) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
            Root<Post> postRoot = criteriaQuery.from(Post.class);
            Join<Post, Community> postCommunityJoin = postRoot.join(Post_.community);
            Join<Community, UserProfile> communityUserProfileJoin = postCommunityJoin.join(Community_.author);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = communityUserProfileJoin.join(UserProfile_.systemUser);
            criteriaQuery.select(postRoot);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(userProfileSystemUserJoin.get(SystemUser_.email), email)),
                                criteriaBuilder.equal(postRoot.get(Post_.id), postId));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Post> getByCommunityId(final Long communityId, final int firstResult, final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
            Root<Community> communityRoot = criteriaQuery.from(Community.class);
            criteriaQuery.select(communityRoot.get(Community_.POSTS));
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(
                communityRoot.get(Community_.id), communityId), criteriaBuilder.equal(
                    communityRoot.get(Community_.isDeleted), false)));
            TypedQuery<Post> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

    @Override
    public List<Post> getByEmail(final String email, final int firstResult, final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Post> subQuery = criteriaBuilder.createQuery(Post.class);
            Root<UserProfile> userProfileRoot = subQuery.from(UserProfile.class);
            Join<UserProfile, Community> userProfileCommunityListJoin = userProfileRoot.join(
                UserProfile_.communitiesSubscribedTo);
            Join<Community, Post> communityPostJoin = userProfileCommunityListJoin.join(
                Community_.posts);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = userProfileRoot.join(UserProfile_.systemUser);
            subQuery.select(userProfileCommunityListJoin.get(Community_.POSTS)).distinct(true);
            subQuery.where(criteriaBuilder.and(criteriaBuilder.equal(userProfileSystemUserJoin.get(
                SystemUser_.email), email), criteriaBuilder.equal(communityPostJoin.get(Post_.isDeleted), false)));
            TypedQuery<Post> typedQuery = entityManager.createQuery(subQuery);
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

}
