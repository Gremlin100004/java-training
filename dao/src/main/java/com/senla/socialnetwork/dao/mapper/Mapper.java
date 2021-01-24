package com.senla.socialnetwork.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T> {

    T transformRow(ResultSet resultSet) throws SQLException;

}
