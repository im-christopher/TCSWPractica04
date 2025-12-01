package org.uv.tcswpractica04;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;

/**
 *
 * @author christopher
 */
public class HibernateUtils {

    private HibernateUtils() {
    }

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Cargar configuración desde hibernate.cfg.xml en el classpath
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
            // Construir la SessionFactory directamente
        } catch (Throwable ex) {
            
            Logger.getLogger(HibernateUtils.class.getName()).log(Logger.Level.ERROR, "Error inicializando SessionFactory: ", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}
