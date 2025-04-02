package ru.kashigin.musichub.service;

import ru.kashigin.musichub.dto.PersonDto;
import ru.kashigin.musichub.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> getAllPersons();
    List<PersonDto> getAllPersonsApi();


    Optional<Person> getPersonById(Long id);
    Optional<PersonDto> getPersonByIdApi(Long id);

    void createPerson(Person person);
    PersonDto createPerson(PersonDto personDto);

    void updatePerson(Long id, Person person);
    PersonDto updatePerson(Long id, PersonDto personDto);

    void deletePerson(Long id);
    Person convertToPerson(PersonDto personDto);
    PersonDto convertToPersonDto(Person person);
}
