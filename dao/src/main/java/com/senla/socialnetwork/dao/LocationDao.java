package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.Location;

public interface LocationDao extends GenericDao<Location, Long> {
    Location getLocation(String email);
}
