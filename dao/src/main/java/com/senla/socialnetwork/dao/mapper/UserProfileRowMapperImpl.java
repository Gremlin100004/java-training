package com.senla.socialnetwork.dao.mapper;

import com.senla.socialnetwork.model.UserProfile;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserProfileRowMapperImpl implements UserProfileRowMapper {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME = "surname";

    @Override
    public UserProfile mapRow(final ResultSet resultSet, final int column) throws SQLException {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(resultSet.getLong(COLUMN_ID));
        userProfile.setName(resultSet.getString(COLUMN_NAME));
        userProfile.setSurname(resultSet.getString(COLUMN_SURNAME));
        return userProfile;
    }

}
