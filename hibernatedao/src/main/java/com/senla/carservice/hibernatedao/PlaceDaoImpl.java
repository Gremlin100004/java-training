package com.senla.carservice.hibernatedao;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Order_;
import com.senla.carservice.domain.Place;
import com.senla.carservice.hibernatedao.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Singleton
public class PlaceDaoImpl extends AbstractDao<Place, Long> implements PlaceDao {

    public PlaceDaoImpl() {
    }

    @Override
    public List<Place> getBusyPlaces(Date executeDate, Session session) {
        LOGGER.debug("Method getFreePlaces");
        LOGGER.trace("Parameter executeDate: {}", executeDate);
        LOGGER.trace("Parameter session: {}",  session);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Place> criteriaQuery = criteriaBuilder.createQuery(Place.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        orderRoot.join(Order_.place);
        criteriaQuery.select(orderRoot.get(Order_.place)).distinct(true);
        criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(orderRoot.get(Order_.leadTime), executeDate));
        Query<Place> query = session.createQuery(criteriaQuery);
        List<Place> places = query.getResultList();
        if (places == null) {
            throw new DaoException("Error getting busy places");
        }
        return places;
    }

    @Override
    public Long getNumberBusyPlaces(Date executeDate, Session session) {
        LOGGER.debug("Method getFreePlaces");
        LOGGER.trace("Parameter executeDate: {}", executeDate);
        LOGGER.trace("Parameter session: {}",  session);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        orderRoot.join(Order_.place);
        criteriaQuery.select(criteriaBuilder.count(orderRoot.get(Order_.place))).distinct(true);
        criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(orderRoot.get(Order_.leadTime), executeDate));
        return session.createQuery(criteriaQuery).getSingleResult();

    }

    @Override
    public Long getNumberPlaces(Session  session) {
        LOGGER.debug("Method getNumberPlaces");
        LOGGER.debug("Parameter session: {}",  session);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Place> placeRoot = criteriaQuery.from(Place.class);
        criteriaQuery.select(criteriaBuilder.count(placeRoot));
        return session.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public Place getPlaceById(Long id, Session  session) {
        LOGGER.debug("Method getPlaceById");
        LOGGER.debug("Parameter id: {}", id);
        LOGGER.debug("Parameter session: {}",  session);
        return session.get(Place.class, id);
    }
}