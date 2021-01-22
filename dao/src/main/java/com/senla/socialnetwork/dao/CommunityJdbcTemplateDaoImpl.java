package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.mapper.CommunityMapper;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
@Slf4j
public class CommunityJdbcTemplateDaoImpl extends AbstractJdbcTemplateDao<Community, Long> implements CommunityDao {
    private static final String SQL_REQUEST_GET_COMMUNITIES = "SELECT communities.id, communities.creation_date, communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities.author_id=user_profiles.id WHERE communities.is_deleted=false OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_COMMUNITIES_BY_TYPE = "SELECT communities.id, communities.creation_date, communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities.author_id=user_profiles.id WHERE communities.is_deleted=false AND communities.type=? OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS = "SELECT communities.id, communities.creation_date, communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities.author_id=user_profiles.id WHERE communities.is_deleted=false ORDER BY (SELECT COUNT(DISTINCT community_user.users_id) FROM community_user WHERE community_user.communities_id=communities.id) OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_COMMUNITIES_BY_EMAIL = "SELECT communities.id, communities.creation_date, communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities.author_id=user_profiles.id INNER JOIN users ON user_profiles.user_id=users.id WHERE users.email=? OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL = "SELECT communities.id, communities.creation_date, communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities.author_id=user_profiles.id INNER JOIN community_user ON communities.id=community_user.communities_id WHERE community_user.users_id=(SELECT user_profiles.id FROM user_profiles INNER JOIN users ON user_profiles.user_id=users.id WHERE users.email=?) OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_ALL_RECORDS = "SELECT communities.id, communities.creation_date, communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities.author_id=user_profiles.id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_FIND_BY_ID = "SELECT communities.id, communities.creation_date, communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities.author_id=user_profiles.id WHERE communities.id=?";
    private static final String SQL_REQUEST_FIND_BY_ID_AND_EMAIL = "SELECT communities.id, communities.creation_date, communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities.author_id=user_profiles.id INNER JOIN users ON user_profiles.user_id=users.id WHERE communities.id=? AND users.email=?";
    private static final String START_FRAGMENT_REQUEST_SAVE_RECORD = "INSERT INTO communities (author_id, type, title, information, creation_date) VALUES (";
    private static final String QUERY_PARAMETER_SEPARATOR = ", ";
    private static final String END_FRAGMENT_REQUEST_SAVE_RECORD = ")";
    private static final String START_FRAGMENT_REQUEST_UPDATE_RECORD = "UPDATE communities SET creation_date=";
    private static final String FIELD_AUTHOR_ID = ", author_id=";
    private static final String FIELD_TYPE = ", type=";
    private static final String FIELD_TITLE = ", title=";
    private static final String FIELD_INFORMATION = ", information=";
    private static final String FIELD_IS_DELETED = ", is_deleted=";
    private static final String FIELD_ID = " WHERE ID=";
    private static final String STRING_HIGHLIGHT_CHARACTER = "'";
    private static final String SQL_REQUEST_DELETE_RECORD = "DELETE FROM communities WHERE ID=?";

    public CommunityJdbcTemplateDaoImpl() {
        setMapperType(CommunityMapper.class);
    }

    @Override
    public List<Community> getCommunities(int firstResult, int maxResults) {
        try {
            return jdbcTemplate.query(
                SQL_REQUEST_GET_COMMUNITIES, new Object[]{firstResult, maxResults}, new CommunityMapper());
        } catch (DataAccessException exception) {
            log.error("[{}]", exception.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Community> getCommunitiesByType(CommunityType communityType, int firstResult, int maxResults) {
        try {
            return jdbcTemplate.query(
                SQL_REQUEST_GET_COMMUNITIES_BY_TYPE, new Object[]{
                    communityType.toString(), firstResult, maxResults}, new CommunityMapper());
        } catch (DataAccessException exception) {
            log.error("[{}]", exception.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Community> getCommunitiesSortiedByNumberOfSubscribers(int firstResult, int maxResults) {
        try {
            return jdbcTemplate.query(
                SQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS, new Object[]{
                    firstResult, maxResults}, new CommunityMapper());
        } catch (DataAccessException exception) {
            log.error("[{}]", exception.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Community> getCommunitiesByEmail(String email, int firstResult, int maxResults) {
        try {
            return jdbcTemplate.query(
                SQL_REQUEST_GET_COMMUNITIES_BY_EMAIL, new Object[]{email, firstResult, maxResults}, new CommunityMapper());
        } catch (DataAccessException exception) {
            log.error("[{}]", exception.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Community> getSubscribedCommunitiesByEmail(String email, int firstResult, int maxResults) {
        try {
            return jdbcTemplate.query(
                SQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL, new Object[]{
                    email, firstResult, maxResults}, new CommunityMapper());
        } catch (DataAccessException exception) {
            log.error("[{}]", exception.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Community findByIdAndEmail(String email, Long communityId) {
        try {
            return jdbcTemplate.queryForObject(
                SQL_REQUEST_FIND_BY_ID_AND_EMAIL, new Object[]{communityId, email}, new CommunityMapper());
        } catch (DataAccessException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    protected String getSaveRequest(Community community) {
        return START_FRAGMENT_REQUEST_SAVE_RECORD + community.getAuthor().getId() + QUERY_PARAMETER_SEPARATOR
                + getString(community.getType().toString()) + QUERY_PARAMETER_SEPARATOR
                + getString(community.getTitle()) + QUERY_PARAMETER_SEPARATOR + getString(community.getInformation())
                + QUERY_PARAMETER_SEPARATOR + getString(community.getCreationDate().toString())
                + END_FRAGMENT_REQUEST_SAVE_RECORD;
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
    protected String getDeleteRequest() {
        return SQL_REQUEST_DELETE_RECORD;
    }

    private String getString(String value) {
        return value != null ? STRING_HIGHLIGHT_CHARACTER + value + STRING_HIGHLIGHT_CHARACTER : null;
    }

}
