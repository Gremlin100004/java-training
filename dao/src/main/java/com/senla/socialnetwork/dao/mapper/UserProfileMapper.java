package com.senla.socialnetwork.dao.mapper;

import com.senla.socialnetwork.domain.UserProfile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfileMapper implements RowMapper<UserProfile> {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME = "surname";

    @Override
    public UserProfile mapRow(ResultSet resultSet, int column) throws SQLException {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(resultSet.getLong(COLUMN_ID));
        userProfile.setName(resultSet.getString(COLUMN_NAME));
        userProfile.setSurname(resultSet.getString(COLUMN_SURNAME));
        return userProfile;
    }

}
