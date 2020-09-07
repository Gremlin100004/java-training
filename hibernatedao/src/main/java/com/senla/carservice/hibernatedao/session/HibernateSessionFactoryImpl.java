package com.senla.carservice.hibernatedao.session;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.hibernatedao.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@Singleton
public class HibernateSessionFactoryImpl implements HibernateSessionFactory {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Override
    public Session getSession() {
        if (session == null || !session.isOpen()) {
            openSession();
        }
        return session;
    }

    @Override
    public void openTransaction() {
        if (session == null || !session.isOpen()) {
            openSession();
        }
        transaction = session.beginTransaction();
    }

    @Override
    public void commitTransaction() {
        if (transaction != null && transaction.isActive()) {
            transaction.commit();
        }
    }

    @Override
    public void rollBackTransaction() {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    }

    @Override
    public void closeSession() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    @Override
    public void closeSessionFactory() {
        if (sessionFactory != null && sessionFactory.isOpen()) {
            sessionFactory.close();
        }
    }

    private void initialize() {
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

    private void openSession() {
        if (sessionFactory == null) {
            initialize();
        }
        session = sessionFactory.openSession();
    }
}