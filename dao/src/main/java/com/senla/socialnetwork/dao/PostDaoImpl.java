package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Community_;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.domain.Post_;
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
public class PostDaoImpl extends AbstractDao<Post, Long> implements PostDao {
    public PostDaoImpl() {
        setType(Post.class);
    }

    @Override
    public List<Post> getByCommunityId(Long communityId, int firstResult, int maxResults) {
        log.debug("[getByCommunityId]");
        log.trace("[communityId: {}, firstResult: {}, maxResults: {}]", communityId, firstResult,  maxResults);
        try {
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
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<Post> getByEmail(String email, int firstResult, int maxResults) {
        log.debug("[getPostsFromSubscribedCommunities]");
        log.trace("[email: {}, firstResult: {},maxResults: {}]", email, firstResult, maxResults);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
            Root<UserProfile> userProfileRoot = criteriaQuery.from(UserProfile.class);
            Join<UserProfile, Community> userProfileCommunityListJoin = userProfileRoot.join(
                UserProfile_.communitiesSubscribedTo);
            Join<Community, Post> communityPostJoin = userProfileCommunityListJoin.join(
                Community_.posts);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = userProfileRoot.join(UserProfile_.systemUser);
            criteriaQuery.select(userProfileCommunityListJoin.get(Community_.POSTS));
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(userProfileSystemUserJoin.get(
                SystemUser_.email), email), criteriaBuilder.equal(communityPostJoin.get(Post_.isDeleted), false)));
            criteriaQuery.orderBy(criteriaBuilder.desc(communityPostJoin.get(Post_.creationDate)));
            TypedQuery<Post> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            typedQuery.setMaxResults(maxResults);
            return typedQuery.getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

}
