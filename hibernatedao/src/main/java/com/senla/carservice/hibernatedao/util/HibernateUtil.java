package com.senla.carservice.hibernatedao.util;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.hibernatedao.exception.DaoException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration()
                .addAnnotatedClass(Master.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Place.class)
                .buildSessionFactory();
        } catch (Throwable e) {
            throw new DaoException("Error initialize SessionFactory");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}