package com.shegoestech;

public class Main {
    /**
     * DTO - Data Transfer Object -  POJO (Plain Old Java Object)
     * DAO -  Data Access Object -  Accessing data from database/datasource
     */
    public static void main(String[] args) throws Exception {
        PersonDao personDao = new PersonDao();
        Person laura = new Person("Laura", "Zveja", "laura.zveja@gmail.com", "Latvia");
        Person angelica = new Person("Angelica", "Gorbaca", "angelica.gorbaca@gmail.com", "Latvia/Cyprus");

        personDao.savePerson(laura);
        personDao.savePerson(angelica);

        System.out.println("-----------------------------------------------------------------------");
        System.out.println(personDao.findById(2L));
        System.out.println("-----------------------------------------------------------------------");
        angelica.setCountry("Cyprus");
        personDao.update(angelica);
        System.out.println(personDao.findById(2L));
        System.out.println("-----------------------------------------------------------------------");

        Person dbLaura = personDao.findById(1L);
        personDao.delete(dbLaura);

        Person isLauraStillThere = personDao.findById(1L);
        System.out.println(isLauraStillThere);
    }
}
