package com.shegoestech;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductDaoTest {
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @InjectMocks
    private ProductDAO productDAO;

    @BeforeEach
    void setUp() {
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
}
