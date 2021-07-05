package com.shegoestech;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
public class ProductDaoTest {
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @Mock
    private EntityManager entityManager;
    @Mock
    private TypedQuery<Product> query;
    @InjectMocks
    private ProductDAO productDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(sessionFactory.openSession())
                .thenReturn(session);
    }

    @Test
    void saveProduct() {
        when(session.beginTransaction())
                .thenReturn(transaction);
        when(session.save(isA(Product.class)))
                .thenReturn("");

        Product sampleProduct = new Product("Product", 2.0, "description", 10);

        productDAO.saveProduct(sampleProduct);

        verify(session).beginTransaction();
        verify(session).save(isA(Product.class));
    }

    @Test
    void findById() throws Exception {
        Long expectedId = 24L;
        Product sampleProduct = new Product("Product", 2.0, "description", 10);
        sampleProduct.setId(expectedId);

        when(session.find(Product.class, expectedId))
                .thenReturn(sampleProduct);
        Product foundProduct = productDAO.findById(expectedId);

        assertEquals(expectedId, foundProduct.getId());
        assertEquals("Product", foundProduct.getName());

        verify(session).find(Product.class, expectedId);
    }

    @Test
    void findById_whenProductNotFound() {
        Long productId = 24L;
        when(session.find(Product.class, productId))
                .thenReturn(null);
        try {
            productDAO.findById(productId);
            fail();
        } catch (Exception e) {
            assertEquals("Product with id 24 does not exist", e.getMessage());
        }

        verify(session).find(Product.class, productId);
    }

    @Test
    void findByName() throws Exception {
        Long expectedId = 24L;
        Product sampleProduct = new Product("Product", 2.0, "description", 10);
        sampleProduct.setId(expectedId);

        when(sessionFactory.createEntityManager())
                .thenReturn(entityManager);
        when(entityManager.createQuery(anyString(), eq(Product.class)))
                .thenReturn(query);
        when(query.setParameter("name", "product"))
                .thenReturn(query);
        when(query.getResultList())
                .thenReturn(Collections.singletonList(sampleProduct));

        List<Product> foundProduct = productDAO.findByName("product");

        assertEquals(1, foundProduct.size());
        assertEquals(expectedId, foundProduct.get(0).getId());
        assertEquals("Product", foundProduct.get(0).getName());

        verify(sessionFactory).createEntityManager();
        verify(entityManager).createQuery(anyString(), eq(Product.class));
        verify(query).setParameter("name", "product");
        verify(query).getResultList();
    }
}
