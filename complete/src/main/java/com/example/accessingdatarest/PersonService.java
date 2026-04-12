package com.example.accessingdatarest;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Iterable<Person> findAll() {
        // Primero intenta leer del cache
        String cached = Cache.getCache().getCacheValue("people");
        if (cached != null) {
            System.out.println("Devolviendo desde cache");
            // Aquí deberías deserializar el JSON a objetos Person
            return new ObjectMapper().readValue(cached, new TypeReference<List<Person>>() {});
        }

        // Si no hay cache, consulta la BD
        Iterable<Person> people = repository.findAll();

        // Guardar en cache como JSON
        String json = new ObjectMapper().writeValueAsString(people);
        Cache.getCache().setCacheValue("people", json);

        return people;
    }

    public Person save(Person person) {
        Person saved = repository.save(person);
        // Invalida el cache
        Cache.getCache().setCacheValue("people", null);
        return saved;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
        // Invalida el cache
        Cache.getCache().setCacheValue("people", null);
    }
}

