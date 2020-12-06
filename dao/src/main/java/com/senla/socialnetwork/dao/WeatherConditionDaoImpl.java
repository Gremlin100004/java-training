package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.WeatherCondition;
import com.senla.socialnetwork.domain.WeatherCondition_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
@Slf4j
public class WeatherConditionDaoImpl extends AbstractDao<WeatherCondition, Long> implements WeatherConditionDao {
    public WeatherConditionDaoImpl() {
        setType(WeatherCondition.class);
    }

    @Override
    public WeatherCondition findByLocation(final Location location) {
        log.debug("[location: {}]", location);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<WeatherCondition> criteriaQuery = criteriaBuilder.createQuery(WeatherCondition.class);
            Root<WeatherCondition> weatherConditionRoot = criteriaQuery.from(WeatherCondition.class);
            criteriaQuery.select(weatherConditionRoot);
            criteriaQuery.where(criteriaBuilder.equal(weatherConditionRoot.get(WeatherCondition_.location), location));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

}
