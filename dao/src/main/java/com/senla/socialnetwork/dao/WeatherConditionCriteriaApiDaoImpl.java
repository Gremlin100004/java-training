package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.Location;
import com.senla.socialnetwork.model.WeatherCondition;
import com.senla.socialnetwork.model.WeatherCondition_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class WeatherConditionCriteriaApiDaoImpl extends AbstractDao<WeatherCondition, Long> implements
                                                                                            WeatherConditionDao {
    public WeatherConditionCriteriaApiDaoImpl() {
        setType(WeatherCondition.class);
    }

    @Override
    public WeatherCondition findByLocation(final Location location) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<WeatherCondition> criteriaQuery = criteriaBuilder.createQuery(WeatherCondition.class);
            Root<WeatherCondition> weatherConditionRoot = criteriaQuery.from(WeatherCondition.class);
            criteriaQuery.select(weatherConditionRoot);
            criteriaQuery.where(criteriaBuilder.equal(weatherConditionRoot.get(WeatherCondition_.location), location));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
