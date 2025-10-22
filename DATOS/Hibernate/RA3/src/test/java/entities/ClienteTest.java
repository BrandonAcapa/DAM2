package entities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import util.HibernateUtil;

public class ClienteTest {
    @Test
    public void createTableTest(){
        var cliente1 = new Cliente(null, "Brandon", "Acapa", "brandon@email.com", 20);
        var cliente2 = new Cliente(null, "Pedro", "García", "pedro@email.com", 30);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(cliente1);
        session.persist(cliente2);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        HibernateUtil.shutdown();
    }
}
