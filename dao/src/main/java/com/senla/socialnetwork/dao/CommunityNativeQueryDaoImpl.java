package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.enumaration.CommunityType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

//@Primary
@Repository
@Slf4j
public class CommunityNativeQueryDaoImpl extends AbstractNativeQueryDao<Community, Long> implements CommunityDao {
    private static final String SQL_REQUEST_GET_COMMUNITIES = "SELECT communities.id, communities.creation_date, "
       + "communities.author_id, communities.type, communities.is_deleted, communities.title, communities.information,"
       + " user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON "
       + "communities.author_id=user_profiles.id WHERE communities.is_deleted=false OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_COMMUNITIES_BY_TYPE = "SELECT communities.id, communities.creation_date,"
       + " communities.type, communities.is_deleted, communities.title, communities.information, communities.author_id,"
       + " user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON "
       + "communities.author_id=user_profiles.id WHERE communities.is_deleted=false AND communities.type=? OFFSET ? "
       + "ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS = "SELECT communities.id, "
       + "communities.creation_date, communities.type, communities.is_deleted, communities.title, communities.author_id,"
       + " communities.information, user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER"
       + " JOIN user_profiles ON communities.author_id=user_profiles.id WHERE communities.is_deleted=false ORDER BY "
       + "(SELECT COUNT(DISTINCT community_user.users_id) FROM community_user WHERE community_user"
       + ".communities_id=communities.id) OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_COMMUNITIES_BY_EMAIL = "SELECT communities.id, communities"
       + ".creation_date, communities.author_id, communities.type, communities.is_deleted, communities.title, "
       + "communities.information, user_profiles.id, user_profiles.name, user_profiles.surname FROM communities "
       + "INNER JOIN user_profiles ON communities.author_id=user_profiles.id INNER JOIN users ON user_profiles"
       + ".user_id=users.id WHERE users.email=? OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL = "SELECT communities.id, communities"
       + ".creation_date, communities.author_id, communities.type, communities.is_deleted, communities.title, "
       + "communities.information, user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER "
       + "JOIN user_profiles ON communities.author_id=user_profiles.id INNER JOIN community_user ON communities"
       + ".id=community_user.communities_id WHERE community_user.users_id=(SELECT user_profiles.id FROM user_profiles "
       + "INNER JOIN users ON user_profiles.user_id=users.id WHERE users.email=?) OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_ALL_RECORDS = "SELECT communities.id, communities.creation_date, "
       + "communities.type, communities.author_id, communities.is_deleted, communities.title, communities.information, "
       + "user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON "
       + "communities.author_id=user_profiles.id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_FIND_BY_ID = "SELECT communities.id, communities.creation_date, communities"
       + ".type, communities.is_deleted, communities.title, communities.information, user_profiles.id, "
       + "communities.author_id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles "
       + "ON communities.author_id=user_profiles.id WHERE communities.id=?";
    private static final String SQL_REQUEST_FIND_BY_ID_AND_EMAIL = "SELECT communities.id, communities.creation_date, "
       + "communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, "
       + "user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities"
       + ".author_id=user_profiles.id INNER JOIN users ON user_profiles.user_id=users.id WHERE communities.id=? "
       + "AND users.email=?";
    private static final String START_FRAGMENT_REQUEST_UPDATE_RECORD = "UPDATE communities SET creation_date=";
    private static final String FIELD_AUTHOR_ID = ", author_id=";
    private static final String FIELD_TYPE = ", type=";
    private static final String FIELD_TITLE = ", title=";
    private static final String FIELD_INFORMATION = ", information=";
    private static final String FIELD_IS_DELETED = ", is_deleted=";
    private static final String FIELD_ID = " WHERE ID=";
    private static final String STRING_HIGHLIGHT_CHARACTER = "'";
    private static final String SQL_REQUEST_DELETE_RECORD = "DELETE FROM communities WHERE ID=?";
    private static final int FIRST_PARAMETER_INDEX = 1;
    private static final int SECOND_PARAMETER_INDEX = 2;
    private static final int THIRD_PARAMETER_INDEX = 3;

    public CommunityNativeQueryDaoImpl() {
        setType(Community.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Community> getCommunities(final int firstResult, final int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try {
            return entityManager
                .createNativeQuery(SQL_REQUEST_GET_COMMUNITIES, Community.class)
                .setParameter(FIRST_PARAMETER_INDEX, firstResult)
                .setParameter(SECOND_PARAMETER_INDEX, maxResults)
                .getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Community> getCommunitiesByType(final CommunityType communityType,
                                                final int firstResult,
                                                final int maxResults) {
        log.debug("[communityType: {}, firstResult: {}, maxResults: {}]", communityType, firstResult, maxResults);
        try {
            return (List<Community>) entityManager
                .createNativeQuery(SQL_REQUEST_GET_COMMUNITIES_BY_TYPE, Community.class)
                .setParameter(FIRST_PARAMETER_INDEX, communityType.toString())
                .setParameter(SECOND_PARAMETER_INDEX, firstResult)
                .setParameter(THIRD_PARAMETER_INDEX, maxResults)
                .getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Community> getCommunitiesSortiedByNumberOfSubscribers(final int firstResult, final int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try {
            return entityManager
                .createNativeQuery(SQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS, Community.class)
                .setParameter(FIRST_PARAMETER_INDEX, firstResult)
                .setParameter(SECOND_PARAMETER_INDEX, maxResults)
                .getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Community> getCommunitiesByEmail(final String email, final int firstResult, final int maxResults) {
        log.debug("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        try {
            return entityManager
                .createNativeQuery(SQL_REQUEST_GET_COMMUNITIES_BY_EMAIL, Community.class)
                .setParameter(FIRST_PARAMETER_INDEX, email)
                .setParameter(SECOND_PARAMETER_INDEX, firstResult)
                .setParameter(THIRD_PARAMETER_INDEX, maxResults)
                .getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Community> getSubscribedCommunitiesByEmail(final String email,
                                                           final int firstResult,
                                                           final int maxResults) {
        log.trace("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        try {
            return entityManager
                .createNativeQuery(SQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL, Community.class)
                .setParameter(FIRST_PARAMETER_INDEX, email)
                .setParameter(SECOND_PARAMETER_INDEX, firstResult)
                .setParameter(THIRD_PARAMETER_INDEX, maxResults)
                .getResultList();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public Community findByIdAndEmail(final String email, final Long communityId) {
        log.debug("[email: {}, messageId: {}]", email, communityId);
        try {
            return (Community) entityManager
                .createNativeQuery(SQL_REQUEST_FIND_BY_ID_AND_EMAIL, Community.class)
                .setParameter(FIRST_PARAMETER_INDEX, communityId)
                .setParameter(SECOND_PARAMETER_INDEX, email)
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
    protected String getUpdateRequest(final Community community) {
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
    protected String getDeleteRequest() {
        return SQL_REQUEST_DELETE_RECORD;
    }

    private String getString(final String value) {
        if (value != null) {
            return STRING_HIGHLIGHT_CHARACTER + value + STRING_HIGHLIGHT_CHARACTER;
        }
        return null;
    }

}
