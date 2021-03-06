package com.senla.socialnetwork.dao.mapper;

import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.enumaration.CommunityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CommunityMapperImpl implements CommunityMapper {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CREATION_DATE = "creation_date";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_INFORMATION = "information";
    private static final String COLUMN_IS_DELETED = "information";
    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    public Community transformRow(final ResultSet resultSet) throws SQLException {
        Community community = new Community();
        community.setId(resultSet.getLong(COLUMN_ID));
        community.setCreationDate(resultSet.getTimestamp(COLUMN_CREATION_DATE));
        community.setAuthor(userProfileMapper.transformRow(resultSet));
        community.setType(CommunityType.valueOf(resultSet.getString(COLUMN_TYPE)));
        community.setTitle(resultSet.getString(COLUMN_TITLE));
        community.setInformation(resultSet.getString(COLUMN_INFORMATION));
        community.setIsDeleted(resultSet.getBoolean(COLUMN_IS_DELETED));
        return community;
    }

}
