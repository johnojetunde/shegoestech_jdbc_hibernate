package com.shegoestech;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonDaoTest {

    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @InjectMocks
    private PersonDao personDao;

    @BeforeEach
    void setUp() {
        when(sessionFactory.openSession())
                .thenReturn(session);
    }

    @Test
    void save() {
        doNothing()
                .when(session).save(any());

        personDao.savePerson(new Person());
    }

    @Test
    void findById() throws Exception {
        Person samplePerson = new Person();
        samplePerson.setId(24L);
        samplePerson.setFirstName("Sample Person name");

        when(session.find(Person.class, anyInt()))
                .thenReturn(samplePerson);

        Person foundPerson = personDao.findById(24L);
        assertEquals(24L, foundPerson.getId());
        assertEquals("Sample Person name", foundPerson.getFirstName());
    }
}