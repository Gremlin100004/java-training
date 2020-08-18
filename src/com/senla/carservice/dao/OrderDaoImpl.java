package com.senla.carservice.dao;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.sql.ResultSet;
import java.util.List;

public class OrderDaoImpl extends AbstractDao implements OrderDao{
    @Override
    protected <T> List<T> parseResultSet(final ResultSet resultSet) {
        return null;
    }

    @Override
    protected <T> String getCreateRequest(final T object) {
        return null;
    }

    @Override
    protected String getReadAllRequest() {
        return null;
    }

    @Override
    protected <T> String getUpdateRequest(final T object) {
        return null;
    }

    @Override
    protected <T> String getDeleteRequest(final T object) {
        return null;
    }
}