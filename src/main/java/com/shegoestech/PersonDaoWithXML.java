package com.shegoestech;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class PersonDaoWithXML {

    public void savePerson(Person person) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactoryWithXMLConfig().openSession();
            transaction = session.beginTransaction();
            session.save(person);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Person findById(Long id) throws Exception {
        Session session = HibernateUtil.getSessionFactoryWithXMLConfig().openSession();
        Person person = session.find(Person.class, id);
        if (person == null) {
            throw new Exception("Person with id " + id + " does not exist");
        }
        return person;
    }

    public Person findByIdOrReturnNull(Long id) throws Exception {
        Session session = HibernateUtil.getSessionFactoryWithXMLConfig().openSession();
        return session.find(Person.class, id);
    }

    public void delete(Person person) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactoryWithXMLConfig().openSession();
            transaction = session.beginTransaction();
            session.delete(person);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Person person) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactoryWithXMLConfig().openSession();
            transaction = session.beginTransaction();
            session.update(person);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
