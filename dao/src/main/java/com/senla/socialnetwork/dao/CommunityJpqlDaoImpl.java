package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
//@Primary
@Slf4j
public class CommunityJpqlDaoImpl extends AbstractJpqlDao<Community, Long> implements CommunityDao {
    private static final String SQL_REQUEST_GET_COMMUNITIES_IDS = "SELECT c.id FROM Community c WHERE c.isDeleted=false";
    private static final String SQL_REQUEST_GET_COMMUNITIES_WITH_PAGINATION = "SELECT c FROM Community c WHERE "
       + "c.id in :ids";
    private static final String SQL_REQUEST_GET_COMMUNITIES_IDS_BY_TYPE = "SELECT c.id FROM Community c WHERE "
       + "c.isDeleted=false AND c.type=?1";
    private static final String SQL_REQUEST_GET_COMMUNITIES_WITH_PAGINATION_SORTIED_BY_NUMBER_OF_SUBSCRIBERS = "SELECT "
       + "c FROM Community c WHERE c.id in :ids ORDER BY size(c.subscribers)";
    private static final String SQL_REQUEST_GET_COMMUNITIES_IDS_BY_EMAIL = "SELECT c.id FROM Community c INNER JOIN "
       + "c.author up INNER JOIN up.systemUser su WHERE su.email=?1 AND c.isDeleted=false";
    private static final String SQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_IDS_BY_EMAIL = "SELECT c.id FROM UserProfile "
       + "up INNER JOIN up.systemUser su INNER JOIN up.communitiesSubscribedTo c WHERE su.email=?1 "
       + "AND c.isDeleted=false";
    private static final String SQL_REQUEST_GET_ALL_RECORDS = "SELECT c.id FROM Community c";
    private static final String SQL_REQUEST_FIND_BY_ID = "SELECT c FROM Community c WHERE c.id=?1";
    private static final String SQL_REQUEST_FIND_BY_ID_AND_EMAIL = "SELECT c FROM Community c INNER JOIN c.author up "
       + "INNER JOIN up.systemUser su WHERE c.id=?1 AND su.email=?2";
    private static final String START_FRAGMENT_REQUEST_UPDATE_RECORD = "UPDATE Community c SET creationDate=";
    private static final String FIELD_AUTHOR_ID = ", author=";
    private static final String FIELD_TYPE = ", type=";
    private static final String FIELD_TITLE = ", title=";
    private static final String FIELD_INFORMATION = ", information=";
    private static final String FIELD_IS_DELETED = ", isDeleted=";
    private static final String FIELD_ID = " WHERE c.id=";
    private static final String STRING_HIGHLIGHT_CHARACTER = "'";
    private static final String SQL_REQUEST_DELETE_RECORD = "DELETE FROM Community c WHERE c.id=?1";

    public CommunityJpqlDaoImpl() {
        setType(Community.class);
    }

    @Override
    public List<Community> getCommunities(final int firstResult, final int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try {
            return getEntityWithPagination(firstResult, maxResults, entityManager.createQuery(
                SQL_REQUEST_GET_COMMUNITIES_IDS, Long.class).getResultList(),
                     SQL_REQUEST_GET_COMMUNITIES_WITH_PAGINATION);
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<Community> getCommunitiesByType(final CommunityType communityType,
                                                final int firstResult,
                                                final int maxResults) {
        log.debug("[communityType: {}, firstResult: {}, maxResults: {}]", communityType, firstResult, maxResults);
        try {
            return getEntityWithPagination(firstResult, maxResults, entityManager.createQuery(
                SQL_REQUEST_GET_COMMUNITIES_IDS_BY_TYPE, Long.class).setParameter(
                1, communityType).getResultList(), SQL_REQUEST_GET_COMMUNITIES_WITH_PAGINATION);
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override

    public List<Community> getCommunitiesSortiedByNumberOfSubscribers(final int firstResult, final int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try {
            return getEntityWithPagination(firstResult, maxResults, entityManager.createQuery(
                SQL_REQUEST_GET_COMMUNITIES_IDS, Long.class).getResultList(),
                     SQL_REQUEST_GET_COMMUNITIES_WITH_PAGINATION_SORTIED_BY_NUMBER_OF_SUBSCRIBERS);
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<Community> getCommunitiesByEmail(final String email, final int firstResult, final int maxResults) {
        log.debug("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        try {
            return getEntityWithPagination(firstResult, maxResults, entityManager.createQuery(
                SQL_REQUEST_GET_COMMUNITIES_IDS_BY_EMAIL, Long.class).setParameter(1, email).getResultList(),
                     SQL_REQUEST_GET_COMMUNITIES_WITH_PAGINATION);
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<Community> getSubscribedCommunitiesByEmail(final String email,
                                                           final int firstResult,
                                                           final int maxResults) {
        log.trace("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        try {
            return getEntityWithPagination(firstResult, maxResults, entityManager.createQuery(
                SQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_IDS_BY_EMAIL, Long.class).setParameter(
                    1, email).getResultList(), SQL_REQUEST_GET_COMMUNITIES_WITH_PAGINATION);
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public Community findByIdAndEmail(final String email, final Long communityId) {
        log.debug("[email: {}, messageId: {}]", email, communityId);
        try {
            return entityManager
                .createQuery(SQL_REQUEST_FIND_BY_ID_AND_EMAIL, Community.class)
                .setParameter(1, communityId)
                .setParameter(2, email)
                .getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    protected String getFindByIdRequest() {
        return SQL_REQUEST_FIND_BY_ID;
    }

    @Override
    protected String getUpdateRequest(Community community) {
        return START_FRAGMENT_REQUEST_UPDATE_RECORD + getString(community.getCreationDate().toString()) + FIELD_AUTHOR_ID
               + community.getAuthor().getId() + FIELD_TYPE + getString(community.getType().toString()) + FIELD_TITLE
               + getString(community.getTitle()) + FIELD_INFORMATION + getString(community.getInformation())
               + FIELD_IS_DELETED + community.getIsDeleted() + FIELD_ID + community.getId();
    }

    @Override
    protected String getReadAllRequest() {
        return SQL_REQUEST_GET_ALL_RECORDS;
    }

    @Override
    protected String getRequestEntitiesWithPagination() {
        return SQL_REQUEST_GET_COMMUNITIES_WITH_PAGINATION;
    }

    @Override
    protected String getDeleteRequest() {
        return SQL_REQUEST_DELETE_RECORD;
    }

    private String getString(String value) {
        return value != null ? STRING_HIGHLIGHT_CHARACTER + value + STRING_HIGHLIGHT_CHARACTER : null;
    }

}
