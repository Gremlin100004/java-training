package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.Location;
import com.senla.socialnetwork.model.WeatherCondition;

public interface WeatherConditionDao extends GenericDao<WeatherCondition, Long> {
    WeatherCondition findByLocation(Location location);

}
