package com.shegoestech;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class PersonDao {

    public void savePerson(Person person) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        Person person = session.find(Person.class, id);
        if (person == null) {
            throw new Exception("Person with id " + id + " does not exist");
        }
        return person;
    }

    public void delete(Person person) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
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
            Session session = HibernateUtil.getSessionFactory().openSession();
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
