package services;

import java.util.List;

public interface Service<T> {
    List<T> getAllItems();

    int deleteItemById(long id);

    T getItemById(long id);
}
