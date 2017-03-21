package dao.hibernate;

import dao.ComponentDao;
import entities.Component;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateComponentDao extends AbstractHibernateDao implements ComponentDao {

    public HibernateComponentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public long create(Component item) {
        Transaction transaction;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
        }
        return 0;
    }

    @Override
    public Component read(int id) {
        return null;
    }

    @Override
    public long update(Component item) {
        return 0;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Component> getAll() {
        return null;
    }

    @Override
    public Component topPrice() {
        return null;
    }
}
