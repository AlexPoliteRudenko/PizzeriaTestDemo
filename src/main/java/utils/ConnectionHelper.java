package utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionHelper {
    private static final String DB = "pizzeria";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionHelper.class);

    private static ComboPooledDataSource cpds;

    public static Connection createConnection() {
        LOGGER.info("connecting to DB: {}", DB);
        try {
            LOGGER.info("Successfully connected to DB: {}", DB);
            return cpds.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB: {}", DB);
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while closing connection to DB: {}", DB);
            throw new RuntimeException(e);
        }
    }

    public static void createConnectionPool() {
        cpds = new ComboPooledDataSource();
        String host = getValueFromProperties("db.host");
        String login = getValueFromProperties("db.login");
        String password = getValueFromProperties("db.password");
        cpds.setJdbcUrl(host);
        cpds.setUser(login);
        cpds.setPassword(password);
    }

    public static void closeConnectionPool() {
        cpds.close();
    }

    private static String getValueFromProperties(String key) {
        String value = "";
        Properties properties = new Properties();
        try (FileReader reader = new FileReader("src/resources/config.properties");) {
            properties.load(reader);
            value = properties.getProperty(key);
            return value;
        } catch (IOException e) {
            LOGGER.error("Unable to load properties");
            throw new RuntimeException(e);
        }
    }
}
