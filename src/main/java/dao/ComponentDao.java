package dao;

import entities.Component;

public interface ComponentDao extends BaseGenericDao<Component> {
    Component topPrice();
}
