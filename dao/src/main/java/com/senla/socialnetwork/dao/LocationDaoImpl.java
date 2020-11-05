package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class LocationDaoImpl extends AbstractDao<Location, Long> implements LocationDao {
    public LocationDaoImpl() {
        setType(Location.class);
    }

}
