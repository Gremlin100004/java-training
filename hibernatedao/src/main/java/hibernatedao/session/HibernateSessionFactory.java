package hibernatedao.session;

import org.hibernate.Session;

public interface HibernateSessionFactory {
    public Session getSession();

    public void openTransaction();

    public void commitTransaction();

    public void rollBackTransaction();

    public void closeSession();
}
