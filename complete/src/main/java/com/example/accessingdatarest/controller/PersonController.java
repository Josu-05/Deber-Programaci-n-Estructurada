package com.example.accessingdatarest.controller;

import com.example.accessingdatarest.Person;
import com.example.accessingdatarest.PersonService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/people")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public Iterable<Person> getAll() {
        return personService.findAll();
    }

    @PostMapping
    public Person save(@RequestBody Person person) {
        return personService.save(person);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        personService.deleteById(id);
    }
}
