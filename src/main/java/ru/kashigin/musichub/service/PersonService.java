package ru.kashigin.musichub.service;

import ru.kashigin.musichub.dto.PersonDto;
import ru.kashigin.musichub.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> getAllPersons();
    Person getPersonById(Long id);
    Person createPerson(Person person);
    Person updatePerson(Long id, Person person);
    void deletePerson(Long id);
    Person convertToPerson(PersonDto personDto);
}
