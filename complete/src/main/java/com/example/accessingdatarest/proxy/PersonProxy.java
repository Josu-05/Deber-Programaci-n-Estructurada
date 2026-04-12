package com.example.accessingdatarest.proxy;

import com.example.accessingdatarest.Person;

public interface PersonProxy {
    Iterable<Person> getAllPersons();
    Person savePerson(Person person);
    void deletePerson(Long id);
}
