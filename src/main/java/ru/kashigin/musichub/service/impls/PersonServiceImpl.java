package ru.kashigin.musichub.service.impls;

import org.springframework.stereotype.Service;
import ru.kashigin.musichub.model.Person;
import ru.kashigin.musichub.repository.PersonRepository;
import ru.kashigin.musichub.service.PersonService;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person getPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Long id, Person person) {
        Optional<Person> existingPersonOptional = personRepository.findById(id);
        if (existingPersonOptional.isPresent()) {
            Person existingPerson = existingPersonOptional.get();
            existingPerson.setName(person.getName());
            existingPerson.setEmail(person.getEmail());
            existingPerson.setPassword(person.getPassword());

            return personRepository.save(existingPerson);
        }
        return null;
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
