package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.Community_;
import com.senla.socialnetwork.model.SystemUser;
import com.senla.socialnetwork.model.SystemUser_;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.model.UserProfile_;
import com.senla.socialnetwork.model.enumaration.CommunityType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

//@Primary
@Repository
public class CommunityCriteriaApiDynamicGraphDaoImpl extends AbstractCriteriaApiDynamicGraphDao<Community, Long> implements CommunityDao {
    private static final String ATTRIBUTE_NAME = "javax.persistence.fetchgraph";

    public CommunityCriteriaApiDynamicGraphDaoImpl() {
        setType(Community.class);
    }

    @Override
    public List<Community> getCommunities(final int firstResult, final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Community> criteriaQuery = criteriaBuilder.createQuery(Community.class);
            Root<Community> communityRoot = criteriaQuery.from(Community.class);
            criteriaQuery.select(communityRoot);
            criteriaQuery.where(criteriaBuilder.equal(communityRoot.get(Community_.isDeleted), false));
            TypedQuery<Community> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setHint(ATTRIBUTE_NAME, getEntityGraph());
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

    @Override
    public List<Community> getCommunitiesByType(final CommunityType communityType,
                                                final int firstResult,
                                                final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Community> criteriaQuery = criteriaBuilder.createQuery(Community.class);
            Root<Community> communityRoot = criteriaQuery.from(Community.class);
            criteriaQuery.select(communityRoot);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(
                communityRoot.get(Community_.type), communityType), criteriaBuilder.equal(communityRoot.get(
                    Community_.isDeleted), false)));
            criteriaQuery.orderBy(criteriaBuilder.desc(communityRoot.get(Community_.creationDate)));
            TypedQuery<Community> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setHint(ATTRIBUTE_NAME, getEntityGraph());
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

    @Override
    public List<Community> getCommunitiesSortiedByNumberOfSubscribers(final int firstResult, final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Community> criteriaQuery = criteriaBuilder.createQuery(Community.class);
            Root<Community> communityRoot = criteriaQuery.from(Community.class);
            criteriaQuery.select(communityRoot);
            criteriaQuery.where(criteriaBuilder.equal(communityRoot.get(Community_.isDeleted), false));
            criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.size(communityRoot.get(Community_.subscribers))));
            TypedQuery<Community> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setHint(ATTRIBUTE_NAME, getEntityGraph());
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

    @Override
    public List<Community> getCommunitiesByEmail(final String email, final int firstResult, final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Community> criteriaQuery = criteriaBuilder.createQuery(Community.class);
            Root<Community> communityRoot = criteriaQuery.from(Community.class);
            Join<Community, UserProfile> communityUserProfileJoin = communityRoot.join(Community_.author);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = communityUserProfileJoin.join(
                UserProfile_.systemUser);
            criteriaQuery.select(communityRoot);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(userProfileSystemUserJoin.get(
                SystemUser_.email), email), criteriaBuilder.equal(communityRoot.get(Community_.isDeleted), false)));
            criteriaQuery.orderBy(criteriaBuilder.desc(communityRoot.get(Community_.creationDate)));
            TypedQuery<Community> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(firstResult);
            typedQuery.setHint(ATTRIBUTE_NAME, getEntityGraph());
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

    @Override
    public List<Community> getSubscribedCommunitiesByEmail(final String email,
                                                           final int firstResult,
                                                           final int maxResults) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Community> criteriaQuery = criteriaBuilder.createQuery(Community.class);
            Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
            Root<UserProfile> userProfileRoot = subquery.from(UserProfile.class);
            Join<UserProfile, Community> userProfileUserProfileJoin = userProfileRoot.join(
                UserProfile_.communitiesSubscribedTo);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = userProfileRoot.join(UserProfile_.systemUser);
            subquery.select(userProfileUserProfileJoin.get(Community_.id));
            Join<UserProfile, Community> userProfileCommunityJoin = userProfileRoot.join(
                UserProfile_.communitiesSubscribedTo);
            subquery.where(criteriaBuilder.and(criteriaBuilder.equal(userProfileSystemUserJoin.get(
                SystemUser_.email), email), criteriaBuilder.equal(userProfileCommunityJoin.get(
                    Community_.isDeleted), false)));
            Root<Community> communityRoot = criteriaQuery.from(Community.class);
            criteriaQuery.where(communityRoot.get(Community_.id).in(subquery));
            TypedQuery<Community> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setHint(ATTRIBUTE_NAME, getEntityGraph());
            typedQuery.setFirstResult(firstResult);
            if (maxResults != 0) {
                typedQuery.setMaxResults(maxResults);
            }
            return typedQuery.getResultList();
    }

    @Override
    public Community findByIdAndEmail(final String email, final Long communityId) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Community> criteriaQuery = criteriaBuilder.createQuery(Community.class);
            Root<Community> communityRoot = criteriaQuery.from(Community.class);
            Join<Community, UserProfile> communityUserProfileJoin = communityRoot.join(Community_.author);
            Join<UserProfile, SystemUser> userProfileSystemUserJoin = communityUserProfileJoin.join(
                UserProfile_.systemUser);
            criteriaQuery.select(communityRoot);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(
                userProfileSystemUserJoin.get(SystemUser_.email), email)), criteriaBuilder.equal(
                    communityRoot.get(Community_.id), communityId));
            TypedQuery<Community> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setHint(ATTRIBUTE_NAME, getEntityGraph());
            return typedQuery.getSingleResult();
    }

    @Override
    protected EntityGraph<?> getEntityGraph() {
        EntityGraph<Community> communityEntityGraph = entityManager.createEntityGraph(Community.class);
        Subgraph<Community> subgraph = communityEntityGraph.addSubgraph(Community_.AUTHOR);
        subgraph.addAttributeNodes(UserProfile_.LOCATION);
        subgraph.addAttributeNodes(UserProfile_.SCHOOL);
        subgraph.addAttributeNodes(UserProfile_.UNIVERSITY);
        return communityEntityGraph;
    }

}
