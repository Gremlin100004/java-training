package hibernatedao.session;

import com.senla.carservice.container.annotation.Singleton;
import hibernatedao.exception.DaoException;
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
        openSession();
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

    private void initialize() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
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