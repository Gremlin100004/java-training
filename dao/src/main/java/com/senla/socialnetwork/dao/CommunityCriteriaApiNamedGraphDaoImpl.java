package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.*;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

//@Primary
@Repository
@Slf4j
public class CommunityCriteriaApiNamedGraphDaoImpl extends AbstractCriteriaApiNamedGraphDao<Community, Long> implements CommunityDao {
    private static final String GRAPH_NAME = "graph.Community";

    public CommunityCriteriaApiNamedGraphDaoImpl() {
        setType(Community.class);
    }

    @Override
    public List<Community> getCommunities(final int firstResult, final int maxResults) {
        log.debug("[getCommunities]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try {
            EntityGraph<?> entityGraph = entityManager.getEntityGraph(GRAPH_NAME);
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Community> criteriaQuery = criteriaBuilder.createQuery(Community.class);
            Root<Community> communityRoot = criteriaQuery.from(Community.class);
            criteriaQuery.select(communityRoot);
            criteriaQuery.where(criteriaBuilder.equal(communityRoot.get(Community_.isDeleted), false));
            TypedQuery<Community> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setHint(ATTRIBUTE_NAME, entityGraph);
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
    public List<Community> getCommunitiesByType(final CommunityType communityType,
                                                final int firstResult,
                                                final int maxResults) {
        log.debug("[getCommunitiesByType]");
        log.debug("[communityType: {}, firstResult: {}, maxResults: {}]", communityType, firstResult, maxResults);
        try {
            EntityGraph<?> entityGraph = entityManager.getEntityGraph(GRAPH_NAME);
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Community> criteriaQuery = criteriaBuilder.createQuery(Community.class);
            Root<Community> communityRoot = criteriaQuery.from(Community.class);
            criteriaQuery.select(communityRoot);
            criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(
                communityRoot.get(Community_.type), communityType), criteriaBuilder.equal(communityRoot.get(
                    Community_.isDeleted), false)));
            criteriaQuery.orderBy(criteriaBuilder.desc(communityRoot.get(Community_.creationDate)));
            TypedQuery<Community> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setHint(ATTRIBUTE_NAME, entityGraph);
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
    public List<Community> getCommunitiesSortiedByNumberOfSubscribers(final int firstResult, final int maxResults) {
        log.debug("[getCommunitiesSortiedByNumberOfSubscribers]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try {
            EntityGraph<?> entityGraph = entityManager.getEntityGraph(GRAPH_NAME);
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Community> criteriaQuery = criteriaBuilder.createQuery(Community.class);
            Root<Community> communityRoot = criteriaQuery.from(Community.class);
            criteriaQuery.select(communityRoot);
            criteriaQuery.where(criteriaBuilder.equal(communityRoot.get(Community_.isDeleted), false));
            criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.size(communityRoot.get(Community_.subscribers))));
            TypedQuery<Community> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setHint(ATTRIBUTE_NAME, entityGraph);
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
    public List<Community> getCommunitiesByEmail(final String email, final int firstResult, final int maxResults) {
        log.debug("[getCommunitiesByEmail]");
        log.debug("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        try {
            EntityGraph<?> entityGraph = entityManager.getEntityGraph(GRAPH_NAME);
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
            typedQuery.setHint(ATTRIBUTE_NAME, entityGraph);
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
    public List<Community> getSubscribedCommunitiesByEmail(final String email,
                                                           final int firstResult,
                                                           final int maxResults) {
        log.debug("[getSubscribedCommunitiesByEmail]");
        log.trace("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        try {
            EntityGraph<?> entityGraph = entityManager.getEntityGraph(GRAPH_NAME);
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
            typedQuery.setHint(ATTRIBUTE_NAME, entityGraph);
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
    public Community findByIdAndEmail(final String email, final Long communityId) {
        log.debug("[findByIdAndEmail]");
        log.debug("[email: {}, messageId: {}]", email, communityId);
        try {
            EntityGraph<?> entityGraph = entityManager.getEntityGraph(GRAPH_NAME);
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
            typedQuery.setHint(ATTRIBUTE_NAME, entityGraph);
            return typedQuery.getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    protected String getGraphName() {
        return GRAPH_NAME;
    }

}
