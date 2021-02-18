package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

@Repository
@Slf4j
public class CommunityCriteriaApiDaoCustomImpl implements CommunityCriteriaApiDaoCustom {
    private static final String ATTRIBUTE_NAME = "javax.persistence.fetchgraph";

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Community> getCommunitiesSortiedByNumberOfSubscribers(final int firstResult, final int maxResults) {
        log.debug("[getCommunitiesSortiedByNumberOfSubscribers]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try {
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
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    private EntityGraph<?> getEntityGraph() {
        EntityGraph<Community> communityEntityGraph = entityManager.createEntityGraph(Community.class);
        Subgraph<Community> subgraph = communityEntityGraph.addSubgraph(Community_.AUTHOR);
        subgraph.addAttributeNodes(UserProfile_.LOCATION);
        subgraph.addAttributeNodes(UserProfile_.SCHOOL);
        subgraph.addAttributeNodes(UserProfile_.UNIVERSITY);
        return communityEntityGraph;
    }

}
