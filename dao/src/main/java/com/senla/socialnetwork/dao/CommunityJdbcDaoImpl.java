package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.exception.DaoException;
import com.senla.socialnetwork.dao.mapper.CommunityMapper;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository

@Slf4j
public class CommunityJdbcDaoImpl extends AbstractJdbcDao<Community, Long> implements CommunityDao {
    private static final String SQL_REQUEST_GET_COMMUNITIES = "SELECT communities.id, communities.creation_date, "
       + "communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, "
       + "user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities"
       + ".author_id=user_profiles.id WHERE communities.is_deleted=false OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_COMMUNITIES_BY_TYPE = "SELECT communities.id, communities.creation_date, "
       + "communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, "
       + "user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities"
       + ".author_id=user_profiles.id WHERE communities.is_deleted=false AND communities.type=? OFFSET ? ROWS FETCH "
       + "NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS = "SELECT communities.id, "
       + "communities.creation_date, communities.type, communities.is_deleted, communities.title, communities"
       + ".information, user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN "
       + "user_profiles ON communities.author_id=user_profiles.id WHERE communities.is_deleted=false ORDER BY "
       + "(SELECT COUNT(DISTINCT community_user.users_id) FROM community_user WHERE community_user"
       + ".communities_id=communities.id) OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_COMMUNITIES_BY_EMAIL = "SELECT communities.id, communities"
       + ".creation_date, communities.type, communities.is_deleted, communities.title, communities.information, "
       + "user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON "
       + "communities.author_id=user_profiles.id INNER JOIN users ON user_profiles.user_id=users.id WHERE users.email=?"
       + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL = "SELECT communities.id, communities"
       + ".creation_date, communities.type, communities.is_deleted, communities.title, communities.information, "
       + "user_profiles.id, user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON "
       + "communities.author_id=user_profiles.id INNER JOIN community_user ON communities.id=community_user"
       + ".communities_id WHERE community_user.users_id=(SELECT user_profiles.id FROM user_profiles INNER JOIN users "
       + "ON user_profiles.user_id=users.id WHERE users.email=?) OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_GET_ALL_RECORDS = "SELECT communities.id, communities.creation_date, "
       + "communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, "
       + "user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities"
       + ".author_id=user_profiles.id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SQL_REQUEST_FIND_BY_ID = "SELECT communities.id, communities.creation_date, "
       + "communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, "
       + "user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities"
       + ".author_id=user_profiles.id WHERE communities.id=?";
    private static final String SQL_REQUEST_FIND_BY_ID_AND_EMAIL = "SELECT communities.id, communities.creation_date, "
       + "communities.type, communities.is_deleted, communities.title, communities.information, user_profiles.id, "
       + "user_profiles.name, user_profiles.surname FROM communities INNER JOIN user_profiles ON communities"
       + ".author_id=user_profiles.id INNER JOIN users ON user_profiles.user_id=users.id WHERE communities.id=? "
       + "AND users.email=?";
    private static final String SQL_REQUEST_SAVE_RECORD = "INSERT INTO communities (author_id, type, title, "
       + "information, creation_date) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_REQUEST_UPDATE_RECORD = "UPDATE communities SET creation_date=?, author_id=?, "
       + "type=?, title=?, information=?, is_deleted=? WHERE ID=?";
    private static final String SQL_REQUEST_DELETE_RECORD = "DELETE FROM communities WHERE ID=?";
    private static final int STATEMENT_WILDCARD_SECOND_INDEX = 2;
    private static final int STATEMENT_WILDCARD_THIRD_INDEX = 3;
    private static final int STATEMENT_WILDCARD_FOURTH_INDEX = 4;
    private static final int STATEMENT_WILDCARD_FIFTH_INDEX = 5;
    private static final int STATEMENT_WILDCARD_SIXTH_INDEX = 6;
    private static final int STATEMENT_WILDCARD_SEVENTH_INDEX = 7;

    @Autowired
    CommunityMapper communityMapper;

    @Override
    public List<Community> getCommunities(final int firstResult, final int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(
            SQL_REQUEST_GET_COMMUNITIES)) {
            statement.setInt(STATEMENT_WILDCARD_FIRST_INDEX, firstResult);
            statement.setInt(STATEMENT_WILDCARD_SECOND_INDEX, maxResults);
            return parseResultSetReturnList(statement.executeQuery());
        } catch (SQLException exception) {
            log.error("[{}]", exception.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Community> getCommunitiesByType(final CommunityType communityType,
                                                final int firstResult,
                                                final int maxResults) {
        log.debug("[communityType: {}, firstResult: {}, maxResults: {}]", communityType, firstResult, maxResults);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(
            SQL_REQUEST_GET_COMMUNITIES_BY_TYPE)) {
            statement.setString(STATEMENT_WILDCARD_FIRST_INDEX, communityType.toString());
            statement.setInt(STATEMENT_WILDCARD_SECOND_INDEX, firstResult);
            statement.setInt(STATEMENT_WILDCARD_THIRD_INDEX, maxResults);
            return parseResultSetReturnList(statement.executeQuery());
        } catch (SQLException exception) {
            log.error("[{}]", exception.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Community> getCommunitiesSortiedByNumberOfSubscribers(final int firstResult, final int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(
            SQL_REQUEST_GET_COMMUNITIES_SORTIED_BY_NUMBER_OF_SUBSCRIBERS)) {
            statement.setInt(STATEMENT_WILDCARD_FIRST_INDEX, firstResult);
            statement.setInt(STATEMENT_WILDCARD_SECOND_INDEX, maxResults);
            return parseResultSetReturnList(statement.executeQuery());
        } catch (SQLException exception) {
            log.error("[{}]", exception.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Community> getCommunitiesByEmail(final String email, final int firstResult, final int maxResults) {
        log.debug("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(
            SQL_REQUEST_GET_COMMUNITIES_BY_EMAIL)) {
            statement.setString(STATEMENT_WILDCARD_FIRST_INDEX, email);
            statement.setInt(STATEMENT_WILDCARD_SECOND_INDEX, firstResult);
            statement.setInt(STATEMENT_WILDCARD_THIRD_INDEX, maxResults);
            return parseResultSetReturnList(statement.executeQuery());
        } catch (SQLException exception) {
            log.error("[{}]", exception.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Community> getSubscribedCommunitiesByEmail(final String email,
                                                           final int firstResult,
                                                           final int maxResults) {
        log.debug("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(
            SQL_REQUEST_GET_SUBSCRIBED_COMMUNITIES_BY_EMAIL)) {
            statement.setString(STATEMENT_WILDCARD_FIRST_INDEX, email);
            statement.setInt(STATEMENT_WILDCARD_SECOND_INDEX, firstResult);
            statement.setInt(STATEMENT_WILDCARD_THIRD_INDEX, maxResults);
            return parseResultSetReturnList(statement.executeQuery());
        } catch (SQLException exception) {
            log.error("[{}]", exception.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Community findByIdAndEmail(final String email, final Long communityId) {
        log.debug("[email: {}, communityId: {}]", email, communityId);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement
            (SQL_REQUEST_FIND_BY_ID_AND_EMAIL)) {
            statement.setLong(STATEMENT_WILDCARD_FIRST_INDEX, communityId);
            statement.setString(STATEMENT_WILDCARD_SECOND_INDEX, email);
            return parseResultSet(statement.executeQuery());
        } catch (SQLException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    protected void fillStatementCreate(final PreparedStatement statement, final Community community) {
        log.debug("[statement: {}, community: {}]", statement, community);
        try {
            statement.setLong(STATEMENT_WILDCARD_FIRST_INDEX, community.getAuthor().getId());
            statement.setString(STATEMENT_WILDCARD_SECOND_INDEX, community.getType().toString());
            statement.setString(STATEMENT_WILDCARD_THIRD_INDEX, community.getTitle());
            statement.setString(STATEMENT_WILDCARD_FOURTH_INDEX, community.getInformation());
            statement.setTimestamp(STATEMENT_WILDCARD_FIFTH_INDEX, new Timestamp(community.getCreationDate().getTime()));
        } catch (SQLException exception) {
            log.error("[{}]", exception.getMessage());
            throw new DaoException("Error fill statement for create request");
        }
    }

    @Override
    protected void fillStatementUpdate(final PreparedStatement statement, final Community community) {
        log.debug("[statement: {}, community: {}]", statement, community);
        try {
            statement.setTimestamp(STATEMENT_WILDCARD_FIRST_INDEX, new Timestamp(community.getCreationDate().getTime()));
            statement.setLong(STATEMENT_WILDCARD_SECOND_INDEX, community.getAuthor().getId());
            statement.setString(STATEMENT_WILDCARD_THIRD_INDEX, community.getType().toString());
            statement.setString(STATEMENT_WILDCARD_FOURTH_INDEX, community.getTitle());
            statement.setString(STATEMENT_WILDCARD_FIFTH_INDEX, community.getInformation());
            statement.setBoolean(STATEMENT_WILDCARD_SIXTH_INDEX, community.getIsDeleted());
            statement.setLong(STATEMENT_WILDCARD_SEVENTH_INDEX, community.getId());
        } catch (SQLException exception) {
            log.error("[{}]", exception.getMessage());
            throw new DaoException("Error fill statement for update request");
        }
    }

    @Override
    protected List<Community> parseResultSetReturnList(final ResultSet resultSet) throws SQLException {
        List<Community> communities = new ArrayList<>();
        while (resultSet.next()) {
            communities.add(communityMapper.transformRow(resultSet));
        }
        return communities;
    }

    @Override
    protected Community parseResultSet(final ResultSet resultSet) throws SQLException {
        return communityMapper.transformRow(resultSet);
    }

    @Override
    protected String getCreateRequest() {
        return SQL_REQUEST_SAVE_RECORD;
    }

    @Override
    protected String getUpdateRequest() {
        return SQL_REQUEST_UPDATE_RECORD;
    }

    @Override
    protected String getFindByIdRequest() {
        return SQL_REQUEST_FIND_BY_ID;
    }

    @Override
    protected String getReadAllRequest() {
        return SQL_REQUEST_GET_ALL_RECORDS;
    }

    @Override
    protected String getDeleteRequest() {
        return SQL_REQUEST_DELETE_RECORD;
    }

}
