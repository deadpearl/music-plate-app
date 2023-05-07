package com.example.musicplate.db;

import com.example.musicplate.models.Cart;
import com.example.musicplate.models.Plate;
import com.example.musicplate.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static Session session;

    public static Session getSession() {
        if (session == null) {
            try {
                Configuration configuration = new Configuration();

                configuration.configure("hibernate.cfg.xml");
                configuration.addAnnotatedClass(Cart.class);
                configuration.addAnnotatedClass(Plate.class);
                configuration.addAnnotatedClass(User.class);

                // Create Session Factory
                SessionFactory sessionFactory
                        = configuration.buildSessionFactory();

                // Initialize Session Object
                session = sessionFactory.openSession();
                return session;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return session;
    }
}
