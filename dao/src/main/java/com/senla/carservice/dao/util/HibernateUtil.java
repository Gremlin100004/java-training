package com.senla.carservice.dao.util;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil implements SessionUtil {

    private SessionFactory sessionFactory;

    @Override
    public Session getSession() {
        if (sessionFactory == null){
            initialize();
        }
        return sessionFactory.getCurrentSession();
    }

    private void initialize(){
        sessionFactory = new Configuration()
            .addAnnotatedClass(Master.class)
            .addAnnotatedClass(Order.class)
            .addAnnotatedClass(Place.class)
            .buildSessionFactory();
    }
}