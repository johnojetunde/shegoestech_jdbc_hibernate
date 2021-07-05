package com.shegoestech;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {

                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/she_goes_tech?serverTimezone=UTC");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "password");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.SHOW_SQL, true);

                //you may change this per project
                settings.put(Environment.HBM2DDL_AUTO, "create");
                /** validate, update, create, create-drop, none
                 *
                 *  validate - ensures that the configuration you've declared is valid (i.e schema is correct).
                 *  This option does not perform any update in the database -
                 *
                 *  update - updates the schema but does not destroy the previous data -  ALTER
                 *
                 *  create - creates the schema, destroy previous data -  DROP - CREATE
                 *
                 *
                 *  create-drop -  creates the schema when the sessionFactory is opened and drop the schema when the SessionFactory is closed
                 *  (i.e when the application is stopped). -  DROP - CREATE - DROP
                 *  none -  this does nothing -
                 */

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Person.class);
                configuration.addAnnotatedClass(Product.class);
                //add more annotated classes

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                //here is a comment
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactoryWithXMLConfig() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(Person.class)
                        .buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactoryWithXML() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration()
                        .addAnnotatedClass(Person.class)
                        .configure("hibernate.cfg.xml")
                        .buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return sessionFactory;
    }
}
