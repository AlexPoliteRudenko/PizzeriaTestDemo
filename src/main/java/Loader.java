import entities.Pizza;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Loader {

    public static void main(String[] args) throws Exception {
        try (SessionFactory sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();) {
            try (Session session = sessionFactory.openSession()) {
                Pizza pizza = session.get(Pizza.class, 1);
                System.out.println(pizza);
                System.in.read();
                System.out.println(pizza.getComponents());
                System.in.read();
                pizza.setName("Mozarrella");
                System.in.read();
                session.update(pizza);
                System.in.read();
                System.out.println(session.get(Pizza.class, 1));

            }
        }
    }
}
