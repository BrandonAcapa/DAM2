package dao;

import entities.Cliente;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
//import org.hibernate.query.Query;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class ClienteDAOImpl implements ClienteDAO{
    @Override
    public List<Cliente> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        // Consulta HQL
        List<Cliente> clientes = session.createQuery("from Cliente", Cliente.class).list();

        // otra manera de hacerlo (en 2 pasos):
//        Query<Cliente> query = session.createQuery("from Cliente", Cliente.class);
//        List<Cliente> clientes = query.list();

        return clientes;
    }

    @Override
    public Cliente findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
//        org.hibernate.query.Query<Cliente> query = session.createQuery("from Cliente where id=:id", Cliente.class);
//
//        query.setParameter("id", id);
//
//        Cliente cliente = query.uniqueResult();

        Cliente cliente = session.find(Cliente.class, id);

        return cliente;
    }

    @Override
    public Cliente create(Cliente cliente) {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//
//        try{
//            session.beginTransaction();
//            session.persist(cliente);
//            session.getTransaction().commit();
//
//            return cliente;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            session.getTransaction().rollback();
//
//            return null;
//        }
        Transaction transaction = null; // Objeto Transaction fuera del try

        // 1. Usa try-with-resources para que la sesión se cierre sola
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // 2. Inicia la transacción
            transaction = session.beginTransaction();

            // 3. Guarda el objeto
            session.persist(cliente);

            // 4. Confirma la transacción
            transaction.commit();

            // 5. Devuelve el objeto Cliente (ya persistido y con ID)
            return cliente;

        } catch (HibernateException e) {
            // 6. Si algo falla, haz rollback y devuelve null
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();

            // 7. Devuelve null para indicar que la creación falló
            return null;
        }
        // 8. No necesitas session.close(), el try-with-resources lo hace por ti

    }

    @Override
    public Cliente update(Cliente cliente) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(cliente);
            transaction.commit();
            return cliente;
        } catch (HibernateException e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();;
            }
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Cliente cliente = session.find(Cliente.class, id);
            if (cliente != null){
                session.remove(cliente);
                transaction.commit();
                return true;
            } else{
                transaction.rollback();
                return false;
            }
        } catch (HibernateException e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}
