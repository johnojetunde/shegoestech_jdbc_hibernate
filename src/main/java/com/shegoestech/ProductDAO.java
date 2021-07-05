package com.shegoestech;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductDAO {
    private final SessionFactory sessionFactory;

    public ProductDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveProduct(Product product) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Product findById(Long id) throws Exception {
        Session session = sessionFactory.openSession();
        Product product = session.find(Product.class, id);
        if (product == null) {
            throw new Exception("Product with id " + id + " does not exist");
        }
        return product;
    }

    public void delete(Product product) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(product);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Product product) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(product);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Product> findByName(String name) throws Exception {
        EntityManager em = sessionFactory.createEntityManager();

        TypedQuery<Product> query = em.createQuery("Select p from Product p where p.name=:name ", Product.class);
        query.setParameter("name", name);

        return query.getResultList();
    }
}


