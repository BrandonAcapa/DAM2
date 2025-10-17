package org.example.entities;
// import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
// import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
public class EmployeeTest {
    @Test
    public void createTablesTest(){
        //Ctrl + P, para rellenar el constructor
        Employee employee1 = new Employee(null,
                "Empleado3",
                "García",
                "empleado3@company.com",
                32,
                40000d,
                true,
                LocalDate.of(1990, 1, 1),
                LocalDateTime.now()
        );
        Employee employee2 = new Employee(null,
                "Empleado4",
                "Perez",
                "empleado4@company.com",
                50,
                60000d,
                true,
                LocalDate.of(1950, 1, 1),
                LocalDateTime.now()
        );
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        //el objeto session es la clase central que nos va a permitir la persistencia
        //Para hacer las operaciones de lectura (SELECT) en BBDD, no es necesario iniciar una transacción
        //las operaciones de escritura (CREATE, UPDATE, DELETE) en BBDD deben ir en un transacción
        session.beginTransaction();
        session.persist(employee1);
        session.persist(employee2);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        HibernateUtil.shutdown();
    }
}
