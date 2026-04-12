package com.example.accessingdatarest.proxy;

import com.example.accessingdatarest.Person;
import com.example.accessingdatarest.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonProxyImpl implements PersonProxy {
    private final PersonRepository personRepository;

    public PersonProxyImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Iterable<Person> getAllPersons() {
        System.out.println("Accediendo al repositorio a través del Proxy...");
        return personRepository.findAll();
    }

    @Override
    public Person savePerson(Person person) {
        System.out.println("Guardando persona mediante Proxy...");
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(Long id) {
        System.out.println("Eliminando persona mediante Proxy...");
        personRepository.deleteById(id);
    }
}
