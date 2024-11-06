package ru.kashigin.musichub.service;

import ru.kashigin.musichub.dto.PersonDto;
import ru.kashigin.musichub.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> getAllPersons();
    Optional<Person> getPersonById(Long id);
    Person createPerson(Person person);
    Person updatePerson(Long id, Person person);
    void deletePerson(Long id);
    Person convertToPerson(PersonDto personDto);
}
