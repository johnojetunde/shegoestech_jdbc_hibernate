package com.shegoestech;

import java.util.List;

public class Main {
    /**
     * DTO - Data Transfer Object -  POJO (Plain Old Java Object)
     * DAO -  Data Access Object -  Accessing data from database/datasource
     */
    public static void main(String[] args) throws Exception {
        PersonDao personDao = new PersonDao(HibernateUtil.getSessionFactory());
//       PersonDaoXml personDao = new PersonDaoXml();
        createExample(personDao);
//        updateExample(personDao);
    }

    private static void updateExample(PersonDao personDao) throws Exception {
        Person laura = personDao.findByIdOrReturnNull(1L);
        if (laura == null) {
            laura = new Person("Laura", "Zveja", "laura.zveja@gmail.com", "Latvia");
            personDao.savePerson(laura);
        } else {
            laura.setCountry("Netherlands");
            personDao.update(laura);
        }

        System.out.println("-----------------------------------------------------------------------");
        System.out.println(laura);
        System.out.println("-----------------------------------------------------------------------");
    }

    private static void createExample(PersonDao personDao) throws Exception {

        /***
         *
         * first time -- create
         Hibernate: drop table if exists person
         Hibernate: create table person (id bigint not null auto_increment, country varchar(255), email varchar(255), first_name varchar(255), last_name varchar(255), phone_number varchar(255), primary key (id)) engine=InnoDB
         Hibernate: alter table person add constraint UK_fwmwi44u55bo4rvwsv0cln012 unique (email)
         Hibernate: select person0_.id as id1_0_0_, person0_.country as country2_0_0_, person0_.email as email3_0_0_, person0_.first_name as first_na4_0_0_, person0_.last_name as last_nam5_0_0_, person0_.phone_number as phone_nu6_0_0_ from person person0_ where person0_.id=?
         Hibernate: insert into person (country, email, first_name, last_name, phone_number) values (?, ?, ?, ?, ?)
         * first time  -- update
         Hibernate: create table person (id bigint not null auto_increment, country varchar(255), email varchar(255), first_name varchar(255), last_name varchar(255), phone_number varchar(255), primary key (id)) engine=InnoDB
         Hibernate: alter table person drop index UK_fwmwi44u55bo4rvwsv0cln012
         Hibernate: alter table person add constraint UK_fwmwi44u55bo4rvwsv0cln012 unique (email)
         Hibernate: select person0_.id as id1_0_0_, person0_.country as country2_0_0_, person0_.email as email3_0_0_, person0_.first_name as first_na4_0_0_, person0_.last_name as last_nam5_0_0_, person0_.phone_number as phone_nu6_0_0_ from person person0_ where person0_.id=?
         Hibernate: insert into person (country, email, first_name, last_name, phone_number) values (?, ?, ?, ?, ?)

         --- second time --- create
         Hibernate: drop table if exists person
         Hibernate: create table person (id bigint not null auto_increment, country varchar(255), email varchar(255), first_name varchar(255), last_name varchar(255), phone_number varchar(255), primary key (id)) engine=InnoDB
         Hibernate: alter table person add constraint UK_fwmwi44u55bo4rvwsv0cln012 unique (email)
         Hibernate: select person0_.id as id1_0_0_, person0_.country as country2_0_0_, person0_.email as email3_0_0_, person0_.first_name as first_na4_0_0_, person0_.last_name as last_nam5_0_0_, person0_.phone_number as phone_nu6_0_0_ from person person0_ where person0_.id=?
         Hibernate: insert into person (country, email, first_name, last_name, phone_number) values (?, ?, ?, ?, ?)

         second time -- update
         Hibernate: select person0_.id as id1_0_0_, person0_.country as country2_0_0_, person0_.email as email3_0_0_, person0_.first_name as first_na4_0_0_, person0_.last_name as last_nam5_0_0_, person0_.phone_number as phone_nu6_0_0_ from person person0_ where person0_.id=?
         Hibernate: update person set country=?, email=?, first_name=?, last_name=?, phone_number=? where id=?

         */
        Person laura = new Person("Laura", "Zveja", "laura.zveja@gmail.com", "Latvia");
        Person angelica = new Person("Angelica", "Gorbaca", "angelica.gorbaca@gmail.com", "Latvia/Cyprus");
        Person angelicaJnr = new Person("Anzelika", "Gorbaca", "anzelika.gorbaca@gmail.com", "Latvia/Cyprus");

        personDao.savePerson(laura);
        personDao.savePerson(angelica);
        personDao.savePerson(angelicaJnr);

        System.out.println("-----------------------------------------------------------------------");
        System.out.println(personDao.findById(2L));
        System.out.println("-----------------------------------------------------------------------");
        angelica.setCountry("Cyprus");
        personDao.update(angelica);
        System.out.println(personDao.findById(2L));
        System.out.println("-----------------------------------------------------------------------");

        Person dbLaura = personDao.findById(1L);
        personDao.delete(dbLaura);

        //Person isLauraStillThere = personDao.findById(1L);
        //System.out.println(isLauraStillThere);

        List<Person> results = personDao.findByName("Gorbaca");
        System.out.println("------- finding by name Gorbaca --------------------------");
        System.out.println(results);
        System.out.println("-----------------------------------------------------------------------");


        ProductDAO productDAO = new ProductDAO();

        Product milk = new Product("Soy milk", 1.90, "Dairy free", 46);
        Product chocolate = new Product("Kinder Surprise", 0.99, "Milk and white chocolate egg", 150);

        productDAO.saveProduct(milk);
        productDAO.saveProduct(chocolate);

        productDAO.delete(chocolate);

        System.out.println("-----------------------------------------------------------------------");
        System.out.println(productDAO.findById(1L));

        milk.setCalories(55);
        productDAO.update(milk);

        System.out.println("-----------------------------------------------------------------------");
        System.out.println(productDAO.findById(1L));
    }

    /**
     * /products -  GET -  get all product
     * /products/{id} -  GET -  get byt id
     * /products -  POST -  create product
     *
     *
     * */
}
