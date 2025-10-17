package util;

import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.*;

public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create registry
                registry = new StandardServiceRegistryBuilder().configure().build();
                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);
                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();
                // Create SessionFactory
                //sessionFactory = metadata.getSessionFactoryBuilder().applyInterceptor(new EmployeeInterceptor().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null)
                    StandardServiceRegistryBuilder.destroy(registry);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null)
            StandardServiceRegistryBuilder.destroy(registry);
    }
}