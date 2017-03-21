package dao.jdbc;

import dao.ComponentDao;
import entities.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcComponentDao implements ComponentDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentDao.class);
    private static final String DB = "pizzeria";
    private static final String PROBLEM_RETRIEVING_DATA = "Problem retrieving data from db {} ";
    private static final String DELETE_FAIL = "Component with id {} deleting fail - no such component found ";
    private static final String READ_FAIL = "Component with id {} read fail - no such component found ";
    private static final String GET_LAST_INSERTED = "SELECT LAST_INSERT_ID();";
    private static final String CREATE_FAIL = "{} creating fail!";
    private Connection connection;

    public JdbcComponentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long create(Component item) {
        String sql = "INSERT INTO components (NAME,PRIZE) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrize());
            if (ps.executeUpdate() != 0) {
                LOGGER.info("{} successfully created!", item);
            } else {
                LOGGER.error("{} creating fail!", item);
                throw new RuntimeException(CREATE_FAIL);
            }
            try (Statement statement = connection.createStatement();) {
                try (ResultSet resultSet = statement.executeQuery(GET_LAST_INSERTED)) {
                    resultSet.next();
                    return resultSet.getLong("LAST_INSERT_ID()");
                }
            } catch (SQLException e) {
                LOGGER.error(READ_FAIL, "last insertion ID");
                throw new RuntimeException(READ_FAIL);
            }
        } catch (SQLException e) {
            LOGGER.error(PROBLEM_RETRIEVING_DATA, DB, e);
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.closeConnection(connection);
        }
    }

    @Override
    public Component read(int id) {
        String sql = "SELECT * FROM components WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createEntity(rs);
            } else {
                LOGGER.info(READ_FAIL, id);
                throw new RuntimeException(DELETE_FAIL);
            }
        } catch (SQLException e) {
            LOGGER.error(PROBLEM_RETRIEVING_DATA, DB);
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.closeConnection(connection);
        }
    }

    @Override
    public long update(Component item) {
        return 0;
    }

    @Override
    public void delete(int id) {
        int result;
        String sql = "DELETE FROM components WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            if (ps.executeUpdate() != 0) {
                LOGGER.info("Component with id {} successfully deleted!", id);
            } else {
                LOGGER.info(DELETE_FAIL, id);
                throw new RuntimeException(DELETE_FAIL);
            }

        } catch (SQLException e) {
            LOGGER.error(PROBLEM_RETRIEVING_DATA, DB, e);
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.closeConnection(connection);
        }
    }

    @Override
    public List<Component> getAll() {
        connection = ConnectionHelper.createConnection();
        List<Component> resultList = new ArrayList<>();
        String sql = "SELECT * FROM components;";
        try (Statement s = connection.createStatement();) {
            try (ResultSet rs = s.executeQuery(sql)) {
                while (rs.next()) {
                    resultList.add(createEntity(rs));
                }
            }
            return resultList;
        } catch (SQLException e) {
            LOGGER.error(PROBLEM_RETRIEVING_DATA, DB);
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.closeConnection(connection);
        }
    }

    private Component createEntity(ResultSet rs) {
        Component component = new Component();
        try {
            component.setId(rs.getInt("ID"));
            component.setName(rs.getString("NAME"));
            component.setPrize(rs.getDouble("PRIZE"));
        } catch (SQLException e) {
            LOGGER.error("Problem parsing data from rs for component ");
            throw new RuntimeException(e);
        }
        return component;
    }

    @Override
    public Component topPrice() {
        return null;
    }
}
