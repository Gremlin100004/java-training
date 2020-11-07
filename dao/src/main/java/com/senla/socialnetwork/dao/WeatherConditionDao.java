package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.WeatherCondition;

public interface WeatherConditionDao extends GenericDao<WeatherCondition, Long> {
    WeatherCondition findByLocation(Location location);

}
