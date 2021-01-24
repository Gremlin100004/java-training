package com.senla.socialnetwork.dao.mapper;

import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.enumaration.CommunityType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommunityRowMapperImpl implements RowMapper<Community> {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CREATION_DATE = "creation_date";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_INFORMATION = "information";
    private static final String COLUMN_IS_DELETED = "information";

    @Override
    public Community mapRow(ResultSet resultSet, int column) throws SQLException {
        Community community = new Community();
        UserProfileRowMapperImpl userProfileMapper = new UserProfileRowMapperImpl();
        community.setId(resultSet.getLong(COLUMN_ID));
        community.setCreationDate(resultSet.getTimestamp(COLUMN_CREATION_DATE));
        community.setAuthor(userProfileMapper.mapRow(resultSet, column));
        community.setType(CommunityType.valueOf(resultSet.getString(COLUMN_TYPE)));
        community.setTitle(resultSet.getString(COLUMN_TITLE));
        community.setInformation(resultSet.getString(COLUMN_INFORMATION));
        community.setIsDeleted(resultSet.getBoolean(COLUMN_IS_DELETED));
        return community;
    }

}
