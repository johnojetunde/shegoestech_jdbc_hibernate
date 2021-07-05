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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonDaoTest {

    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @InjectMocks
    private PersonDao personDao;

    @BeforeEach
    void setUp() {
        when(sessionFactory.openSession())
                .thenReturn(session);
    }

    @Test
    void save() {
        when(session.beginTransaction())
                .thenReturn(transaction);

        when(session.save(isA(Person.class)))
                .thenReturn("");

        personDao.savePerson(new Person());

        verify(session).save(isA(Person.class));
    }

    @Test
    void findById() throws Exception {
        Long expectedId = 24L;
        Person samplePerson = new Person();
        samplePerson.setId(expectedId);
        samplePerson.setFirstName("Sample Person name");

        when(session.find(Person.class, 24L))
                .thenReturn(samplePerson);

        Person foundPerson = personDao.findById(expectedId);

        assertEquals(expectedId, foundPerson.getId());
        assertEquals("Sample Person name", foundPerson.getFirstName());

        verify(session).find(Person.class, 24L);
    }
}