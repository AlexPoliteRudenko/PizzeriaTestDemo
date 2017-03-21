import dao.jdbc.JdbcComponentDao;
import entities.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConnectionHelper;
import utils.HibernateSessionFactory;


public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ConnectionHelper.createConnectionPool();

        JdbcComponentDao jdbcComponentDao = new JdbcComponentDao(ConnectionHelper.createConnection());

        System.out.println("GET ALL COMPONENTS:");
        System.out.println(jdbcComponentDao.getAll().toString());
        jdbcComponentDao = new JdbcComponentDao(ConnectionHelper.createConnection());
        System.out.println("GET COMPONENT WITH ID = 4:");
        System.out.println(jdbcComponentDao.read(4).toString());
        jdbcComponentDao = new JdbcComponentDao(ConnectionHelper.createConnection());
        Component component = new Component();
        component.setName("Garlic");
        component.setPrize(70);
        System.out.println("CREATE COMPONENT WITH " + component + " values: ");
        System.out.println("Success! The new ID for this component:" + jdbcComponentDao.create(component));
        ConnectionHelper.closeConnectionPool();

        HibernateSessionFactory.closeSessionFactory();
    }
}
