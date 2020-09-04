package com.senla.carservice.hibernatedao.session;

import org.hibernate.Session;

public interface HibernateSessionFactory {

    Session getSession();

    void openTransaction();

    void commitTransaction();

    void rollBackTransaction();

    void closeSession();
}
