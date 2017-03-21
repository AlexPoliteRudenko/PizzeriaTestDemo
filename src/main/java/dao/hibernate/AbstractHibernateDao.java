package dao.hibernate;

import org.hibernate.SessionFactory;

public abstract class AbstractHibernateDao {

    protected SessionFactory sessionFactory;

    public AbstractHibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
