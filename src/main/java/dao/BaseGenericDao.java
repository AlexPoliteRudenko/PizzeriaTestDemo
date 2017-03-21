package dao;

import java.util.List;

public interface BaseGenericDao<T> {

    long create(T item);

    T read(int id);

    long update(T item);

    void delete(int id);

    List<T> getAll();
}
